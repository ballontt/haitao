package com.haitao.portal.service.thrift;

import com.haitao.portal.service.thrift.zookeeper.ThriftServerAddressProvider;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ballontt on 2017/3/16.
 */
public class ThriftServiceClientProxyFactory implements FactoryBean,InitializingBean,Closeable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Object clientProxy;
    private Integer maxActive = 32; //最大活跃连接数
    private Integer idleTime = 180000; //连接最大空闲时间，default 3min
    private ThriftServerAddressProvider serverAddressProvider;
    private GenericObjectPool<TServiceClient> pool;
    private Class<?> interfaceClass;
    private ThriftClientPoolFactory.PoolOperationCallBack callBack = new ThriftClientPoolFactory.PoolOperationCallBack() {
        @Override
        public void destroy(TServiceClient client) {
            System.out.println("TServerCleint destroy");
        }

        @Override
        public void make(TServiceClient client) {
            System.out.println("TServerCleint created");
        }
    };

    public ThriftServiceClientProxyFactory() {
        super();
    }


    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }


    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public void setServerAddressProvider(ThriftServerAddressProvider serverAddressProvider) {
        this.serverAddressProvider = serverAddressProvider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取服务的接口 Iface
        interfaceClass = classLoader.loadClass(serverAddressProvider.getService() + "$Iface");

        //加载Client.Factory类
        Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(serverAddressProvider.getService() + "$Client$Factory");
        TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();

        //实例化生成client的类
        ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(serverAddressProvider,clientFactory,callBack);

        //对象池的设置
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = this.maxActive;
        poolConfig.minIdle = 0;
        poolConfig.minEvictableIdleTimeMillis = idleTime;
        poolConfig.timeBetweenEvictionRunsMillis = idleTime/2L;

        //生成对象连接池,每次从这里取client连接
        pool = new GenericObjectPool<TServiceClient>(clientPool,poolConfig);

        //生成client的代理
        clientProxy = Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //每次执行服务的方法都通过一个新的client，thrift的连接池是用在这里，代理类只有一个
                TServiceClient client = pool.borrowObject();
                boolean flag = true;
                try{
                    return method.invoke(client,args);
                } catch (Exception e) {
                    flag = false;
                    throw e;
                }
                finally {
                    if(flag) {
                        pool.returnObject(client);
                    }else {
                        pool.invalidateObject(client);
                    }
                }
            }
        });
    }

    @Override
    public Object getObject() throws Exception {
        return clientProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void close() throws IOException {
        //如果代理类销毁了，基本认为是客户端没了
        if(pool != null) {
            try {
                pool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(serverAddressProvider != null) {
            serverAddressProvider.close();
        }
    }
}
