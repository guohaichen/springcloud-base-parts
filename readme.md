### 项目启动流程
1. 启动nacos,官网下载一个nacos版本，我这里本地使用的是[Nacos 2.X](https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.zip)
2. 测试的话可以使用单机模式下的nacos 启动方式为定位到Nacos目录下bin目录，使用 startup.cmd-m standalone 单机启动;
3. 分别启动项目服务 `user-service`、`order-service`、`gateway`;
4. feign-common-api这里作为统一接口引入到order-service和user-service里面;
5. 浏览器输入 localhost:8848/nacos, 可以查看到nacos的服务已经启动好了。并能能看到已启动的项目服务。

### 服务介绍

- `sys-gateway`:网关层对外暴露的接口；配置文件中做了path路由断言predicates，将符合规则的uri请求（根据各服务在nacos注册的服务名） 匹配路由进行转发；同时增加了token过滤器的功能，对未登录用户返回未认证状态码，`sys-security-oauth`的登录接口已暴露，不需要携带token即可通过网关访问；
- `sys-security-oauth`：使用到了security+jwt+redis对服务进行认证和授权；
- `user-service`
- `order-service`
- `feign-common-api`

#### sys-security-oauth

**spring security**

大概流程：

1. 自定义security配置类，需要继承`WebSecurityConfigurerAdapter`，在配置类中关闭默认表单登录，配置认证，授权失败处理器，加密，filter等；
2. 登录接口完成用户名密码校验后，使用AuthenticationManager.authenticate完成用户的认证；
3. UserDetailsService接口：加载用户特定数据的核心接口。其中loadUserByUsername()定义了一个根据用户名查询用户信息的方法(手动实现 返回UserDetails，所以一般也自定义一个类继承UserDetails封装自己的数据)。
4. 自定义一个filter继承OncePerRequestFilter拦截token，对token进行校验；

**todo**:

1. 密码在保存时未加密，明文存储在数据库和SpringContext中；应使用加密器；
2. redis存储token令牌未设置过期时间，且还需要考虑令牌的续期；
3. 服务中只返回了json响应，未使用全局异常处理器捕获异常做统一返回；也可由网关去处理，在网关层面实现全局异常处理；
4. 授权只做了简单的demo，应设计表存储在数据库实时授权；如RBAC模型
