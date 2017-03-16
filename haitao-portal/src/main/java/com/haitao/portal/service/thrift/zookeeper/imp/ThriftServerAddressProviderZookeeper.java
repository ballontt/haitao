package com.haitao.portal.service.thrift.zookeeper.imp;

import com.haitao.portal.service.thrift.zookeeper.ThriftServerAddressProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ballontt on 2017/3/15.
 */
public class ThriftServerAddressProviderZookeeper implements ThriftServerAddressProvider,InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String service;
    private String version="1.0.0";
    private PathChildrenCache childrenCache;
    private CuratorFramework zkClient;

    private Set<String> trace = new HashSet<String>(); //trace用来备份thrift server地址，防止zookeeper故障时可用
    private final List<InetSocketAddress> containor = new ArrayList<InetSocketAddress>();//存储读取到的thrift server Ip
    private Queue<InetSocketAddress> inner = new LinkedList<InetSocketAddress>();//使用queue来完成负载均衡的server ip选择

    private ReentrantLock lock = new ReentrantLock(); //同步锁
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final Integer DEFAULT_WEIGHT = 1; //默认权重

    public ThriftServerAddressProviderZookeeper() {
    }

    public ThriftServerAddressProviderZookeeper(Logger logger, CuratorFramework zkClient) {
        this.logger = logger;
        this.zkClient = zkClient;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(zkClient.getState() == CuratorFrameworkState.LATENT) {
            zkClient.start();
        }
        buildPathChildrenCache(zkClient,getServicePath(),true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        countDownLatch.await();

    }

    private String getServicePath() {
        return "/" + service + "/" + version;
    }

    private void buildPathChildrenCache(CuratorFramework zkClient, String path, Boolean cacheData) {

        //生成PathChildCache对象
        childrenCache = new PathChildrenCache(zkClient,path,cacheData);

        //监听对象
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework zkClient, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type eventType =  event.getType();
                switch(eventType) {
                    case CONNECTION_RECONNECTED:
                        logger.info("Connection is reconnection.");
                        break;
                    case CONNECTION_SUSPENDED:
                        logger.info("Connection is suspended.");
                        break;
                    case CONNECTION_LOST:
                        logger.info("Connection is error,waiting...");
                        return;
                    default:
                }
                childrenCache.rebuild(); //每当监控的节点有变化就重构cache中的内容，cache中缓存了子节点的信息
                rebuild(); //将cache中的信息读入到前面定义的容器中
                countDownLatch.countDown();
            }

            protected void rebuild() throws UnsupportedEncodingException {
                List<ChildData> childDatas =  childrenCache.getCurrentData();
                if(childDatas == null || childDatas.isEmpty()) {
                    containor.clear();
                    logger.error("thrift server-cluster error...");
                    return;
                }
                List<InetSocketAddress> current = new ArrayList<InetSocketAddress>();
                String path = null;
                for(ChildData data : childDatas) {
                    path = data.getPath();
                    logger.debug("childPath:"+path);
                    path = path.substring(getServicePath().length()+1);
                    logger.debug("serviceAddress:"+path);
                    String address = new String(path.getBytes(),"utf-8");
                    current.addAll(transfer(address));
                    trace.add(address);
                }
                Collections.shuffle(current);
                lock.lock();
                containor.clear();
                containor.addAll(current);
                inner.clear();
                inner.addAll(current);
                lock.unlock();
            }

        };
        //PathChildrenCache 中加入监听
        childrenCache.getListenable().addListener(childrenCacheListener);
    }
    //工具函数，将127.0.0.1:1024:1地址转换为InetSocketAddress类型
    private List<InetSocketAddress> transfer(String address) {
        String[] hostname =  address.split(":");
        Integer weight = 1;
        if(hostname.length == 3) {
            weight = Integer.valueOf(hostname[2]);
        }
        String ip = hostname[0];
        Integer port = Integer.valueOf(hostname[1]);
        List<InetSocketAddress> result = new ArrayList<InetSocketAddress> ();
        for(int i=0; i<weight; i++) {
            result.add(new InetSocketAddress(ip,port));
        }
        return result;
    }

    @Override
    public String getService() {
        return service;
    }

    @Override
    public List<InetSocketAddress> findServerAddressList() {
        return Collections.unmodifiableList(containor);
    }

    @Override
    public synchronized InetSocketAddress selector() {
        if(inner.isEmpty()){ //thrift server和zookeeper server连接断开后，childrenListener会清空inner和containor
            if(!containor.isEmpty()) {
                inner.addAll(containor);
            } else if(!trace.isEmpty()) {
                for(String hostname : trace) {
                    containor.addAll(transfer(hostname));
                }
                inner.addAll(containor);
            }
        }
        return inner.poll(); //弹出队列中第一个
    }

    @Override
    public void close() {
        try {
            childrenCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zkClient.close();
        }

    }
}
