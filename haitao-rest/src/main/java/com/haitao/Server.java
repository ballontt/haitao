package com.haitao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server
{
        public static void main(String[] args) {
            //服务的启动写在了ThriftServiceServerFactory的init()方法中，bean被IOC加载后就会自动执行
            ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-service.xml");
        }
 }
