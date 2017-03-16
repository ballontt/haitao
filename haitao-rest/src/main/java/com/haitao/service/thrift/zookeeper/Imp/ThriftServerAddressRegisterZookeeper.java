package com.haitao.service.thrift.zookeeper.Imp;

import com.haitao.service.thrift.zookeeper.ThriftServerAddressRegister;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ballontt on 2017/3/14.
 * 负责将服务和本机地址注册到zookeeper服务器
 */
public class ThriftServerAddressRegisterZookeeper implements ThriftServerAddressRegister {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //连接zookeeper的客户端
    private CuratorFramework zkClient;

    //constructor
    public ThriftServerAddressRegisterZookeeper(){}
    public ThriftServerAddressRegisterZookeeper(CuratorFramework zkClient){
        this.zkClient = zkClient;
    }

    public CuratorFramework getZkClient() {
        return zkClient;
    }

    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    //向zookeeper注册动作
    @Override
    public void register(String service, String version, String address){
        if(zkClient.getState() == CuratorFrameworkState.LATENT) {

            zkClient.start();
            logger.info("连接zookeeper成功");
        }
        if(version==null||"".equals(version.trim())){
            version = "1.0.0";
        }
        try {
            zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/"+service+"/"+version+"/"+address);
        } catch (Exception e) {
            logger.error("ThriftServerAddressRegisterZookeeper向zookeeper注册服务异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        zkClient.close();
    }
}
