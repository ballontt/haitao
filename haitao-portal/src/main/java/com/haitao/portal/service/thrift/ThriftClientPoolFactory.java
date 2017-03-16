package com.haitao.portal.service.thrift;

import com.haitao.portal.service.thrift.zookeeper.ThriftServerAddressProvider;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.net.InetSocketAddress;

/**
 * Created by ballontt on 2017/3/15.
 * 到thrift server的连接池
 */
public class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient> {
    private ThriftServerAddressProvider thriftServerAddressProvider;
    private TServiceClientFactory<TServiceClient> clientFactory;
    private PoolOperationCallBack callback;

    public ThriftClientPoolFactory() {
    }

    public ThriftClientPoolFactory(ThriftServerAddressProvider thriftServerAddressProvider, TServiceClientFactory<TServiceClient> clientFactory) {
        this.thriftServerAddressProvider = thriftServerAddressProvider;
        this.clientFactory = clientFactory;
    }

    public ThriftClientPoolFactory(ThriftServerAddressProvider thriftServerAddressProvider, TServiceClientFactory<TServiceClient> clientFactory, PoolOperationCallBack callback) {
        this.thriftServerAddressProvider = thriftServerAddressProvider;
        this.clientFactory = clientFactory;
        this.callback = callback;
    }

    static interface PoolOperationCallBack{
        //销毁client前执行
        void destroy(TServiceClient client);

        //创建client成功时执行
        void make(TServiceClient client);
    }


    @Override
    public void destroyObject(TServiceClient client) throws Exception {
        if(callback != null) {
            callback.destroy(client);
        }
        TTransport in = client.getInputProtocol().getTransport();
        in.close();
    }

    @Override
    public boolean validateObject(TServiceClient client) {
        TTransport in = client.getInputProtocol().getTransport();
        return in.isOpen();
    }

    @Override
    public void activateObject(TServiceClient client) throws Exception {
        super.activateObject(client);
        TTransport in = client.getInputProtocol().getTransport();
        if(!in.isOpen()) {
            in.open();
        }
    }

    @Override
    public void passivateObject(TServiceClient obj) throws Exception {
        super.passivateObject(obj);
    }

    @Override
    public TServiceClient makeObject() throws Exception {

        InetSocketAddress address = thriftServerAddressProvider.selector();

        TSocket socket = new TSocket(address.getHostName(),address.getPort());
        TTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient client = this.clientFactory.getClient(protocol);
        transport.open();
        if(callback != null) {
            callback.make(client);
        }
        return client;
    }
}
