# Redis相关

 - [数据类型](#数据类型)
 
 - [Redis持久化方式](#Redis持久化方式)
 
 - [使用redis实现限制前台点击](#使用redis实现限制前台点击)
 
 - [redis和memcached的区别](#redis和memcached的区别)
 
 - [redis单线程模型](#redis单线程模型)
 
 - [redis过期策略](#redis过期策略)
 
 - [内存淘汰策略](#内存淘汰策略)
 
 - [保证redis高并发和高可用和一致性](#保证redis高并发和高可用和一致性)
 
 - [redis主从复制](#redis主从复制)
 
 - [集群脑裂导致数据丢失的问题](#集群脑裂导致数据丢失的问题)
 
 
 
 
 
 
 
 ### 数据类型
 
     string  最大存储容量是512M
     hash    最大存储2^32-1,适合存对象
     set     最大存储2^32-1,自动去重
     list    最大存储2^32-1,文章评论列表、粉丝，通过lrange实现分页
     zset    最大存储2^32-1,排序去重,可以通过zrevrange key 0 3查询前三名，zbank key返回排名
 
 ### Redis持久化方式
  
     因为假如redis宕机了那么redis缓存到内存中的数据如果不进行持久化，那么就会导致数据完全丢失，如果把数据进行持久化那么即
     使宕机也不会导致所有数据丢失。实现灾难恢复的。
     1、RDB：将内存中的数据存储到磁盘中（每隔一段时间生成redis内存中的数据的一份的完整的快照）----间隔时间通常是几分钟
            优点：可以做冷备（由redis控制时长，生成快照文件）
            缺点：出现故障的话丢数据比较多，不适合做第一优先恢复的方案设置间隔不要过长，否则导致redis性能下降
     2、AOF：将内存中的数据以日志的形式添加到文件中（通过rewrite的形式，重新生成新的AOF文件，使用新的AOF文件删除旧的AOF）
            append-only-----间隔时间通常是1秒
            优点：可以做冷备（需要写脚本各种定时），文件不容易出现破碎及数据丢失更少
            缺点：占用磁盘空间大，写AOF操作慢，数据恢复的时候比较慢，做冷备需要写脚本各种调度时间
        总结：
            RDB恢复数据相对AOF更加快,因为RDB存储的是数据直接进行恢复，而AOF存储的是指令然后通过指令日志来恢复数据.
            RDB特别适合做冷备，因为恢复数据比较快，读写速度影响小。
            如果同时开启的话，选AOF因为AOF的间隔时间短，数据更加完整 
                
 ### 使用redis实现限制前台点击
     
     前台创建一个uuid+时间戳传给后台，调用redis的自增方法，将key中存储的数字值自增1，如果调用中key不存在，那么先把key进行
     初始化在调用redis的INCR方法，如果值包含错误的类型或者字符串类型的值不能表示为数字，那么返回一个错误。  
 
 ### redis和memcached的区别
 
     1、redis是单线程的(避免多线程的轮询切换，非阻塞IO多路复用+纯内存事件处理器)，memcached是多线程的。
     2、redis数据结构和操作要比memcached要多，支持更多的复杂场景。
     3、redis目前官方支持redis cluster集群模式，memcached没有原生的集群模式，需要依赖客户端往集群分片中写入数据。 
     
 ### redis单线程模型
    
     1、文件事件处理器(IO多路复用)  
     2、客户端------>redis的通信
        A:客户端1(socket01)请求redis的server socket建立连接，server socket接收到AE_READABLE事件，redis进程里的IO多
          路复用把此socket01的事件放到一个queue队列中，redis通过文件事件分发器进行处理分发给连接回答处理器，由连接回答处理
          器将socket01的AE_RAADABLE事件与命令请求处理器进行关联。     
        B:客户端发起set(KV)操作的流程
          客户端的1(socket01)请求set(KV)操作与redis的server socket建立连接，server socket接收到AE_READABLE事件，由
          redis进程里的IO多路复用把socket01的事件放到queue中，此时redis进程里的事件分派器对队列任务里的事件进行分派给命令
          请求处理器由命令请求处理器把AE_READABLE存储到内存中并将AE_WRITABLE关联命令回复处理器，对socket01的set操作进行
          返回ok，最后解除AE_READABLE事件与命令请求处理器的关联，并解除AE_WRITABLE事件与命令回复处理器的关联。
     3、非阻塞IO多路复用
        只把事件押入队列中，不处理所以很快
     4、事件处理器
        主要是纯内存，快速处理
 
 ### redis过期策略
    
     定期删除:每隔多少毫秒随机筛选一部分key进行删除
     惰性删除:用户查询的时候检查key是否过期如果过期了，就再删除。
     如果内存占用过多:没有删除，也没有进行过期时间设置，就走内存淘汰策略
 
 ### 内存淘汰策略
    
     前提:内存不足以容纳新写入数据时
     noeviction:新写入就报错，没人用
     allkeys-lru:在键空间中，移除最近最少使用的key，常用的
     allkeys-random:在键空间中，随机移除key，没人用
     volatile-lru:从已设置过期时间的key中，移除最近最少使用的key，一般不太合适
     volatile-random:从已设置过期时间的key中，随机移除key，没人用
     volatile-ttl:从已设置过期时间的key中，挑选将要过期的key进行移除
         
 ### 保证redis高并发和高可用和一致性
     
     高可用：99.99%
       failover（主备迁移）,slave切换为master，及一旦master故障slave就会自动切换为mastersentinal node(哨兵)会监控
       master node看有没有死，同时也会监控slave node如果master node 死了就会切换slave node为master node
       哨兵：sentinal
       集群监控
       消息通知
       故障转移
       配置中心
       1、至少3个实例
          为什么是至少是3是因为假如以一个master服务A挂掉了，那么此时另外两个slave服务B的哨兵2、服务C的哨兵3就会判定服务A
          挂掉了，majority还有1个那么，服务B和服务C就会选举一个来顶替服务A假如是两个实例，如果master挂掉了那么majority
          就没有了，就不会进行故障转移.
       不可用：master宕机导致缓存就不能用，导致大量数据直接流入数据库从而导致数据库宕机的情况
 
 ### redis主从复制   
 
      master-slave及一主多从，主负责写，并将数据同步复制到其他slave从服务节点中，所有的读请求读slave节点
      redis replication：(复制)
        利用redis主从架构实现-->读写分离架构---->实现可支持水平扩展的搞并发架构
        redis采用异步复制的方式复制数据到slave节点,master采用持久化机制（RDB、AOF）
        复制：
          slave第一次连接master通过ping的形式那么master将会full resynchronization全量复制，如果slave已经连接过了则
          采用部分复制的方式。master的快照文件缓存到RDB,批量由RDB缓存到slave内存中
          主从复制的断电续传
            主从复制思路：
              1、slave请求master然后在本地保存master node的host和IP
              2、slave内部有定时任务每次都会check是否有master要连接，如果有则跟master建立socket连接
              3、如果master配置requirepass那么slave发送masterauther口令进行认证
              4、如果slave和master是第一次连接那么master启用全量复制，将自己全量数据都同步到slave
              5、每次master接收到更新的数据都异步发送给slave node
              A、master和slave会维护一个offset记录累加位置
              B、backlog
                  master node有一个backlog默认大小1M,master node给slave node复制数据时也会将数据在backlog中同步写
                  一份，backlog主要用来作为全量复制中的增量复制的
              C、run id:保证唯一
      主从复制导致的数据丢失问题
      问题描述：客户端请求master写入一条数据，之后master相应给客户端说已经ok,此时master正在往slave进行异步主从复制数据，
               这个时候突然master宕机了，导致数据还没同步完slave导致的问题
 
 ### 集群脑裂导致数据丢失的问题
    
     问题描述：master node       slave node----------sentinal cluster
            由于网络的问题master主节点内出现了异常性的有相同数据，相同工作的两个节点(就是sentinal cluster和slave node
            节点能够正常连接通信，master node主节点和【slave node、sentinal node】都失去了连接，导致sentinal node以
            为master node节点已经挂了，但是实际上master node节点并没有挂，此时sentinal node又重新选取一个slave node
            为主节点，导致此时有两个主节点)
            脑裂就会导致数据丢失的问题：假如client还和之前的master node节点进行通信导致数据存储在这个master node节点上，
            那么如果网络恢复了，就会导致新的master node往此旧的master node节点进行同步数据，此时旧的master node节点上
            的数据就会丢失。
     
     解决脑裂主从复制及脑裂的问题：
         设置:
             min-slaves-to-write 1
             min-slaves-max-lag 10
             此设置在redis配置文件中，设置过之后假如master node及主节点发现向slave node节点同步数据的时候延迟大于10秒，
             那么master node就拒绝接收客户端的请求，起到降级的作用，避免数据大量丢失。

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆