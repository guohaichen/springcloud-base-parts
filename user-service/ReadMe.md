### nacos java-spi
需要引入nacos-client依赖
```xml
<dependency>
    <groupId>com.alibaba.nacos</groupId>
    <artifactId>nacos-client</artifactId>
    <version>${version}</version>
</dependency>
```
#### 配置管理

##### 获取配置

> 用于服务启动的时候从Nacos获取配置

```java
public String getConfig(String dataId, String group, long timeoutMs) throws NacosException;
```

| 参数名  | 参数类型 | 描述                                     |
| :------ | :------- | :--------------------------------------- |
| dataId  | string   | 配置 ID                                  |
| group   | string   | 配置分组                                 |
| timeout | long     | 读取配置超时时间，单位 ms，推荐值 3000。 |

🔴配置管理提供了 ConfigService 接口实现配置的查询，更新，创建，监听配置，删除监听配置 SPI；

```java
public static final String dataId = "nacos-dev-config";
public static final String group = "dev";

//端口号默认8848
String serverAddr = "localhost";
Properties properties = new Properties();
properties.put("serverAddr", serverAddr);
//重点为ConfigService接口；
ConfigService configService = NacosFactory.createConfigService(properties);
//content为整个配置项
String content = initNacosConfigService().getConfig(dataId, group, 5000);
//例如 输出 server.port=8080;

```

##### 监听配置

```java
#listener为监听器，配置变更进入监听器的回调函数。
configService.addListener(String dataId, String group, Listener listener);
```

##### 删除监听配置

> 取消监听器，取消监听后配置不会再被推送；

```java
configService.removeListener(String dataId, String group, Listener listener);
```

##### 发布配置

> 用于程序自动发布Nacos配置。**注意：**创建和修改配置时使用的同一个发布接口，当配置不存在时会创建配置，当配置已存在时会更新配置。

```java
//content为具体配置项
configService.publishConfig(String dataId, String group, String content);
```

##### 删除配置

> 用于通过程序自动删除 Nacos 配置, **注意：** 当配置已存在时会删除该配置，当配置不存在时会直接返回true。

```java
lconfigService.removeConfig(String dataId, String group);
```

