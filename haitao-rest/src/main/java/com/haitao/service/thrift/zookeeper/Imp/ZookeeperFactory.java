package com.haitao.service.thrift.zookeeper.Imp;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by ballontt on 2017/3/14.
 * 管理curator客户端到zookeeper服务器的连接
 */
public class ZookeeperFactory implements FactoryBean<CuratorFramework> {

    private final static String ROOT="rpc";
    //zookeeper服务器地址
    private String zkHost;
    //session超时时间
    private int sessionTimeout = 30000;
    //连接超时时间
    private int connectionTimeout = 30000;
    //共享一个zk连接
    private boolean singleton = true;
    //全局path前缀，用来区分不同的应用
    private String namespace;
    private CuratorFramework zkClient;

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public CuratorFramework getZkClient() {
        return zkClient;
    }

    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public CuratorFramework getObject() throws Exception {
        if(singleton) {
            if(zkClient == null) {
                zkClient = this.create();
                zkClient.start();
            }
            return zkClient;
        }
        return this.create();
    }

    //创建curator客户端
    private CuratorFramework create() {
        if(this.namespace.isEmpty()) {
            this.namespace = ROOT;
        }else{
            this.namespace = this.ROOT + "/" + this.namespace;
        }
        return create(zkHost,sessionTimeout,connectionTimeout,namespace);
    }

    private static  CuratorFramework create(String connectString,int sessionTimeout,int connectionTimeout,String namespace) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        return builder.connectString(connectString).sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(connectionTimeout)
                .canBeReadOnly(true).namespace(namespace).retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .defaultData(null).build();
    }
    public void close() {
        if(zkClient != null) {
            zkClient.close();
        }
    }
}
