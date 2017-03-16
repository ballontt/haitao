package com.haitao.service.thrift.zookeeper;

/**
 * Created by ballontt on 2017/3/14.
 */
public interface ThriftServerAddressRegister {
    void register(String service,String version,String address);
}
