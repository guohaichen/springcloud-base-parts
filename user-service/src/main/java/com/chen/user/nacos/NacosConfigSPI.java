package com.chen.user.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;


/**
 * nacos java-spi 配置中心 练习
 */
public class NacosConfigSPI {
    public static final String dataId = "nacos-dev-config";
    public static final String group = "dev";


    public static void main(String[] args) throws IOException {


        //监听配置 || 取消监听配置
        /*listenNacosConfig();
        //因为监听配置 的listener事件是守护线程，主线程结束守护线程就会退出，触发不了listen，故
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        //发布配置
        publishConfig();

        //拉取nacos配置
        getNacosConfig();

    }

    private static void publishConfig() {
        //发布配置 用于通过程序自动发布Nacos配置，以便通过自动化手段降低运维成本，注意，这里配置已存在时会删除该配置，配置不存在时候会直接返回成功消息；
        try {
            ConfigService configService = initNacosConfigService();
            boolean b = configService.publishConfig("nacos-test", group, "gender: male");
            if (b) {
                System.out.println("发布成功~");
            }
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听nacos配置，如果有配置推送更新，会触发listen事件
     */
    private static void listenNacosConfig() {
        try {
            ConfigService configService = initNacosConfigService();
            //取消监听配置
            //configService.removeListener();
            configService.addListener(dataId, group, new Listener() {
                //configInfo 这里是指配置变更的时候通过回调函数返回该值
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("receive :" + configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建ConfigService
     */
    private static ConfigService initNacosConfigService() throws NacosException {
        //端口号默认8848
        String serverAddr = "localhost";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        return configService;
    }

    /**
     * 通过nacos 提供的spi -> ConfigService获取nacos配置
     */
    private static void getNacosConfig() {
        try {
            String content = initNacosConfigService().getConfig(dataId, group, 5000);
            System.out.println(content);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}


