package com.chen.user.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.serviceloader.ServiceFactoryBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 服务发现sdk
 */
@Slf4j
public class NacosServiceSDK {
    public static void main(String[] args) throws InterruptedException {
        String serviceName = "nacos.test";
        String ip = "localhost";
        int port = 8848;
        String clusterName = "dev-cluster";

        //注册实例
//        registerService(serviceName, ip, port, clusterName);

        //注销实例
//        deregisterService(ip, port);

        //获取全部实例
//        getAllInstance(ip, port);

        //监听服务
        subscribeService(ip);

    }

    /**
     * 监听服务下的实例列表变化
     */
    private static void subscribeService(String ip) throws InterruptedException {
        try {
            NamingService namingService = NamingFactory.createNamingService(ip);
            //sys-gateway为在nacos注册的服务名
            namingService.subscribe("sys-gateway", new EventListener() {
                @Override
                public void onEvent(Event event) {
                    if (event instanceof NamingEvent) {
                        NamingEvent namingEvent = (NamingEvent) event;
                        // 处理服务事件
                        log.info("{} 实例发生事件", namingEvent);
                    }
                }
            });
        } catch (
                NacosException e) {
            throw new RuntimeException(e);
        }
        // 保持程序运行，监听服务事件
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void getAllInstance(String ip, int port) {
        try {
            NamingService namingService = NamingFactory.createNamingService(ip);
            List<Instance> allInstances = namingService.getAllInstances("sys-gateway");
            for (Instance allInstance : allInstances) {
                System.out.println("服务实例:\t" + allInstance.toString());
            }
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除服务下的一个实例
     *
     * @param ip   服务实例ip
     * @param port 实例端口
     */
    private static void deregisterService(String ip, int port) {
        try {
            NamingService namingService = NamingFactory.createNamingService(ip);
            namingService.deregisterInstance("instance_name", ip, port);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册服务
     *
     * @param serviceName 服务名
     * @param ip          服务实例ip
     * @param port        实例端口
     * @param clusterName 集群名
     */
    public static void registerService(String serviceName, String ip, int port, String clusterName) {
        try {
            //通过NamingFactory 接口创建 NamingService 操作
            NamingService namingService = NamingFactory.createNamingService(ip);
            //创建服务
            namingService.registerInstance(serviceName, ip, port, clusterName);

            //更详细的服务注册，可以指定更多服务注册细节，注意需要避免主线程退出，否则会nacos注册就结束了，nacos网页上是看不到的；
            Instance instance = new Instance();
            instance.setIp("127.0.0.1");
            instance.setPort(8888);
            instance.setHealthy(true);
            //元数据
            HashMap<String, String> instanceMeta = new HashMap<>();
            instanceMeta.put("info", "666");
            instance.setMetadata(instanceMeta);
            namingService.registerInstance("instance", instance);
            System.in.read();
        } catch (NacosException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
