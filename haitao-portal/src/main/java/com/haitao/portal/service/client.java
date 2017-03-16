package com.haitao.portal.service;

import com.haitao.portal.service.thrift.ThriftServiceClientProxyFactory;
import com.haitao.service.TTbItemCatService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ballontt on 2017/3/16.
 */
public class client {
    public static void main(String[] args) {
//        simple();
        spring();
    }

    public static void spring() {
       final ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:spring/spring-service.xml");
        TTbItemCatService.Iface tbItemCatService =(TTbItemCatService.Iface) context.getBean("tbItemCatService");
        try {
            System.out.println(tbItemCatService.queryTreeItemCat());
            //关闭连接的勾子，在JVM关闭前调用这个函数,做资源的清除，这里是调用代理类的close()来关闭连接池和地址提供器
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    Map<String,ThriftServiceClientProxyFactory>
                            clientMap = context.getBeansOfType(ThriftServiceClientProxyFactory.class);
                    for(Map.Entry<String, ThriftServiceClientProxyFactory> client : clientMap.entrySet()){
                        System.out.println("serviceName : "+client.getKey() + ",class obj: "+client.getValue());
                        try {
                            client.getValue().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void simple() {
        try {
            TSocket socket = new TSocket("127.0.0.1",3423);
            TTransport transport = new TFramedTransport(socket);
            TProtocol protocol = new TBinaryProtocol(transport);
            TTbItemCatService.Client client = new TTbItemCatService.Client(protocol);
            transport.open();
            System.out.println(client.queryTreeItemCat());
            Thread.sleep(3000);
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
