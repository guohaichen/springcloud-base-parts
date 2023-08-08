### 项目启动流程
1. 启动nacos,官网下载一个nacos版本，我这里本地使用的是[Nacos 2.X](https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.zip)
2. 测试的话可以使用单机模式下的nacos 启动方式为定位到Nacos目录下bin目录，使用 startup.com -m standalone 单机启动;
3. 分别启动项目服务 `user-service`、`order-service`、`gateway`;
4. feign-common-api这里作为统一接口引入到order-service和user-service里面;
5. 浏览器输入 localhost:8848/nacos, 可以查看到nacos的服务已经启动好了。并能能看到已启动的项目服务。

### 程序访问
- 可单独访问user-service,order-service;
- 通过网关接口localhost:10010/.../?auth=admin 访问order-service/user-service。这里要带参数auth=admin因为gateway配置了全局过滤器;

### 服务改造:

使用spring security + oauth2.0改造服务，在gateway层面结合spring security+oauth2.0做登录认证；