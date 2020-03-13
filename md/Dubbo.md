# Dubbo相关

 - [SpringCloud与Dubbo的区别](#SpringCloud与Dubbo的区别)
 
 - [DUBBO的工作原理](#DUBBO的工作原理)
 
 - [dubbo中如果多一个服务提供者，服务消费者是如何感知的](#Dubbo中如果多一个服务提供者服务消费者是如何感知的)
 
 - [注册中心挂掉的情况下还会通信吗](#注册中心挂掉的情况下还会通信吗)
 
 - [Dubbo协议](#Dubbo协议)
 
 - [Dubbo支持的序列化协议](#Dubbo支持的序列化协议)
 
 - [Dubbo负载均衡策略](#Dubbo负载均衡策略)
 
 - [集群容错策略](#集群容错策略)
 
 - [Dubbo动态代理策略](#Dubbo动态代理策略)
 
 - [SPI](#SPI)
 
 - [服务治理](#服务治理)
 
 - [服务降级](#服务降级)
 
 - [失败重试和超时重试](#失败重试和超时重试)
 
 
 
 ### SpringCloud与Dubbo的区别
 
     注册中心不同:Dubbo(zookeeper)、SpringCloud(Eureka)
     服务调用方式不同:Dubbo(RPC)、SpringCloud(Rest API)
     服务网管:Dubbo(无)、SpringCloud(Spring Cloud Netflix Zuul)
     断路器:Dubbo(不完善)、SpringCloud(Spring Cloud Netflix Hystrix)
     分布式配置:Dubbo(无)、SpringCloud(Spring Cloud Config)
     
 ### DUBBO的工作原理
     
     1、service接口层（provider和consumer）
     2、config层进行配置文件配置
     3、proxy层：代理层无论consumer还是provider，double都会提供代理，代理之间进行网络通信
     4、registry层：provider注册自己作为一个服务，consumer就可以找注册中心去寻找自己要调用的服务。
     5、cluster层：provider可以部署到多台机器上的，多个provider就可以组成一个集群
     6、monitor层：consumer调用provider调用了多少次啊？统计监控调用次数
     7、protocol层：负责具体的provider和consumer之间调用接口的时候的网络通信
     8、exchange层：信息交换层，主要做信息交换
     9、serialize层：序列化层
     大致说一下：A服务为服务提供方
               服务提供方起来之后会向注册中心注册服务信息，注册中心就会知道A服务部署了几台并且并记录IP信息，consumer
               服务消费方向注册中心发送请求看是否有A服务，如果有A服务那么都部署在哪台机器上，consumer和provider通过
               proxy代理层进行通信，如果A服务有多台那么dubbo就通过cluster实现负载均衡，使consumer服务消费方的请求
               均匀的分布到A服务及provider的不同机器上。然后通过monitor监控中心监控consumer和provider之间的调用次
               数信息等。
 
 ### Dubbo中如果多一个服务提供者服务消费者是如何感知的
     
     1、注册中心，服务提供者、服务消费者三者之间均为长连接，监控中心除外
     2、注册中心通过长连接感知服务提供者的存在，服务提供者宕机，注册中心立即推送事件通知消费者
     3、注册中心和监控中心全部宕机，不影响已运行的提供者和消费者，消费者在本地缓存了提供者列表
     4、注册中心和监控中心都是可选的，服务消费者可以直连服务提供者
     5、监控中心宕掉不影响使用，只是丢失部分采样数据
     6、数据库宕掉后，注册中心仍能通过服务列表查询，但不能注册新服务
     7、注册中心对等集群，任意一台宕掉后，将自动切换到另一台
     8、注册中心全部宕掉后，服务提供者和服务消费者仍能通过本地缓存通讯
     9、服务提供者无状态，任意一台宕掉后，不影响使用
     10、服务提供者全部宕掉后，服务消费者应用将无法使用，并无限次重连等待服务提供者恢复
     
 ### 注册中心挂掉的情况下还会通信吗
     
     会，因为consumer会缓存一份服务提供方的信息通过代理层进行通信。 
      
 ### Dubbo协议
     
     dubbo协议:
        默认dubbo协议，单一长连接NIO异步通信,使用hessian作为序列化协议单一长连接：consumer和provider之间建立一个连接
        进行通信，不是那种发送个请求建立一个连接用完之后再销毁，而是一直会存在当consumer再次发送数据的时候还是用当前连接
        。适合高并发的情况，因为不需要建立太多连接，适合consumer的数量远远大于provider的数量。
     rmi协议：短连接，java序列化
     hessian协议：短连接
     http协议：走json协议
     webservice协议：走SOAP文本序列化
     
 ### Dubbo支持的序列化协议
 
     dubbo 支持 hession、Java 二进制序列化、json、SOAP 文本序列化多种序列化协议。但是 hessian 是其默认的序列化协议。
        Hessian 的数据结构:
          Hessian 的对象序列化机制有 8 种原始类型：
            原始二进制数据
            boolean
            64-bit date（64 位毫秒值的日期）
            64-bit double
            32-bit int
            64-bit long
            null
            UTF-8 编码的 string
          另外还包括 3 种递归类型:
            list for lists and arrays
            map for maps and dictionaries
            object for objects
          还有一种特殊的类型:
            ref：用来表示对共享对象的引用。
        为什么 PB 的效率是最高的?
          可能有一些同学比较习惯于 JSON or XML 数据存储格式，对于 Protocol Buffer 还比较陌生。Protocol Buffer 其实是
          Google 出品的一种轻量并且高效的结构化数据存储格式，性能比 JSON、XML 要高很多。
          其实 PB 之所以性能如此好，主要得益于两个：
            第一，它使用 proto 编译器，自动进行序列化和反序列化，速度非常快，应该比 XML 和 JSON 快上了 20~100 倍；
            第二，它的数据压缩效果好，就是说它序列化后的数据量体积小。因为体积小，传输起来带宽和速度上会有优化。 
                 
 ### Dubbo负载均衡策略
     
     random loadbalance:随机策略
                        默认策略，按照权重随机分配，权重越大分配流量越高
     roundrobin loadbalance:轮询策略
                            轮询的分配到不同的provider上，针对于机器性能也可以调节权重
     leastactive loadbalance:最少活跃调用策略
                             根据调用provider的活跃度请求的策略
     consistanthash loadbalance:一致性hash策略
                                相同参数的请求一定会分发到同一个provider上，provider挂掉的情况会基于虚拟节点均匀分配
                                剩余的流量，抖动不是很大，如果业务要求一类请求都打到同一个provider那么就用一致性hash策略。
                                
 ### 集群容错策略
     
     failover cluster模式：失败自动切换，默认模式，如果服务宕了，那么请求多次之后感知到服务宕了就会把请求打到其他服务上去。
     failfast cluster模式：一次调用失败就立即失败，写操作
     failsafe cluster模式：出现异常时忽略掉，用于日志记录
     failback cluster模式：失败后后台记录并定时重发
     forking cluster模式：并行调用多个provider只要有一个成功就返回
     broadcast cluster模式：逐个调用provider只要又一个成功就返回    
 
 ### Dubbo动态代理策略
 
     默认使用javasist动态字节码生成，创建代理类，但是可以通过spi扩展机制配置自己的动态代理策略。   
     
 ### SPI
     
     service provider interface服务提供接口
     就是一个接口有多个实现，通过配置可以制定某个实现
     一般用于插件扩展
     经典的如jdbc,jdk提供了好多连接jdbc接口但是没有提供相应实现，都是通过引jar包实现连接不同的数据库。
     原理：自己写个项目并按照dubbo的配置SPI的要求进行写对应的接口，然后在文件中配置对应的key及实现类的目录值，然后在provider
          项目中把打好的jar依赖进来，然后配置xml使其指向对应的key，当启动provider的时候就会加载自己的实现类。 
 
 ### 服务治理
     
     服务太多如何管理
     调用链路自动生成
     服务访问压力及时长统计
     监控
  
 ### 服务降级
    
     服务之间掉不通，走备用逻辑，基于mock配置一下
        
 ### 失败重试和超时重试
     
     都是现成的dubbo有提供，直接配置就行
     设置timeout：200ms
     设置retries:3                                 
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆