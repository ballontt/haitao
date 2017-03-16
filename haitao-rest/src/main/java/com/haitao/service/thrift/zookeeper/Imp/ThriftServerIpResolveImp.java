package com.haitao.service.thrift.zookeeper.Imp;

import com.haitao.service.thrift.zookeeper.ThriftServerIpResolve;

/**
 * Created by ballontt on 2017/3/14.
 * 获取本机IP
 */
public class ThriftServerIpResolveImp implements ThriftServerIpResolve{
    private String Ip = "192.168.6.117";

    @Override
    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }
}
