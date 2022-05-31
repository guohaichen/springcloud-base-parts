将Feign的Client抽取为独立模块，并且把接口有关的POJO、默认的Feign配置都放到这个模块中，提供给所有消费者使用。

例如，将UserClient、User、Feign的默认配置都抽取到一feign-common-api包中，所有微服务引用该依赖包，即可直接使用。

这个feign-common-api就是用来让其他微服务直接依赖这个包，降低耦合度。