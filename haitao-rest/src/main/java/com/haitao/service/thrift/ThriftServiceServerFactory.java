package com.haitao.service.thrift;

import com.haitao.service.thrift.zookeeper.Imp.ThriftServerIpResolveImp;
import com.haitao.service.thrift.zookeeper.ThriftServerAddressRegister;
import com.haitao.service.thrift.zookeeper.ThriftServerIpResolve;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Created by ballontt on 2017/3/14.
 * 启动thrift的rpc服务，并调用ThriftServerAddressRegister向zookeeper上进行注册
 */
public class ThriftServiceServerFactory {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Integer port = 7890;//默认端口
    private Object service; //service实现类
    private String version; //服务版本号
    private Integer weight = 1; //权重
    private ThriftServerIpResolve thriftServerIpResolve; //解决本机IP
    private ThriftServerAddressRegister thriftServerAddressRegister; //向zookeeper上进行注册
    private ThriftServerThread thriftServerThread;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public ThriftServerIpResolve getThriftServerIpResolve() {
        return thriftServerIpResolve;
    }

    public void setThriftServerIpResolve(ThriftServerIpResolve thriftServerIpResolve) {
        this.thriftServerIpResolve = thriftServerIpResolve;
    }

    public ThriftServerAddressRegister getThriftServerAddressRegister() {
        return thriftServerAddressRegister;
    }

    public void setThriftServerAddressRegister(ThriftServerAddressRegister thriftServerAddressRegister) {
        this.thriftServerAddressRegister = thriftServerAddressRegister;
    }

    void init() throws Exception {
        if(this.thriftServerIpResolve == null) {
            this.thriftServerIpResolve = new ThriftServerIpResolveImp();
        }
        String serverIp = this.thriftServerIpResolve.getIp();
        if(serverIp.isEmpty()) {
            logger.error("无法发现Ip地址");
            throw new Exception("无法发现Ip地址");
        }
        String hostname =serverIp + ":" + port +":" + weight;
        //在启动thrift的服务端时，和服务类相关的就只有TProcessor，所以需要根据在xml文件中注册的服务名来反射出TProcessor
        //下面通过service.class找到实现的接口Iface，然后定位接口Iface在哪个类（假设Hello）里定义，在那类(Hello)里找到 Hello.processor
        //然后通过对processor反射得到TProcessor
        TProcessor processor = null;
        String serviceName = null;
        Class<?> serviceClass = service.getClass();
        Class<?>[] interfaces = serviceClass.getInterfaces();
        if(interfaces.length == 0) {
            logger.error("服务的实现类没有实现服务接口Iface");
            throw new Exception("服务的实现类没有实现服务接口Iface");
        }
        for(Class<?> clazz : interfaces) {
            if(!clazz.getSimpleName().equals("Iface")) {
                continue;
            }
            serviceName = clazz.getEnclosingClass().getName();
            String pname = serviceName + "$Processor";
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> pclass = classLoader.loadClass(pname);
            if(!TProcessor.class.isAssignableFrom(pclass)) {
                continue;
            }
            Constructor<?> constructor = pclass.getConstructor(clazz);
            processor = (TProcessor) constructor.newInstance(service);
            break;
        }
        if(processor == null) {
            logger.error("TProcessor反射创建失败,服务的实现类没有实现服务接口Iface");
            throw new Exception("TProcessor反射创建失败,服务的实现类没有实现服务接口Iface");
        }
        //新开启一个线程运行thrift的server服务，因为server方法是阻塞的
        thriftServerThread = new ThriftServerThread(processor,port);
        thriftServerThread.start();

        //向zookeeper上注册服务
        if(thriftServerAddressRegister != null) {
            this.thriftServerAddressRegister.register(serviceName,version,hostname);
        }
    }

    public void close() {
        thriftServerThread.stopServer();
    }

    //启动thrift服务的线程类
    class ThriftServerThread extends Thread{
        private TServer server;
        public ThriftServerThread(TProcessor processor,int port) throws TTransportException {
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
            TProcessorFactory tProcessorFactory = new TProcessorFactory(processor);
            tArgs.processorFactory(tProcessorFactory);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory( new TBinaryProtocol.Factory(true, true));
            server = new TThreadedSelectorServer(tArgs);
        }
        @Override
        public void run() {
            //启动服务
            logger.info("thrift rpc服务:"+service.getClass().getSimpleName()+"开始启动");
            server.serve();
            logger.info("thrift rpc服务:"+service.getClass().getSimpleName()+"启动成功");
        }

        public void stopServer() {
            server.stop();
        }
    }
}
