package com.haitao.portal.service.thrift.zookeeper;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by ballontt on 2017/3/15.
 */
public interface ThriftServerAddressProvider {
    String getService();
    List<InetSocketAddress> findServerAddressList();
    InetSocketAddress selector();
    void close();
}
