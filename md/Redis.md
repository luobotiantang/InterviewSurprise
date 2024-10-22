# Redis相关

 - [数据类型](#数据类型)
 
 - [Redis三大利器](#Redis三大利器)
 
 - [Redis持久化方式](#Redis持久化方式)
 
 - [使用redis实现限制前台点击](#使用redis实现限制前台点击)
 
 - [Redis和Memcached的区别](#Redis和Memcached的区别)
 
 - [redis单线程模型](#redis单线程模型)
 
 - [redis过期策略](#redis过期策略)
 
 - [内存淘汰策略](#内存淘汰策略)
 
 - [保证redis高并发和高可用和一致性](#保证redis高并发和高可用和一致性)
 
 - [redis主从复制](#redis主从复制)
 
 - [集群脑裂导致数据丢失的问题](#集群脑裂导致数据丢失的问题)
 
 - [宕机](#宕机)
 
 - [redis集群模式](#redis集群模式)
 
 - [分布式数据存储的核心算法](#分布式数据存储的核心算法)
 
 - [缓存穿透、缓存击穿、缓存雪崩](#缓存穿透与缓存击穿与缓存雪崩)
 
 - [保证缓存与数据库的双写的一致性](#保证缓存与数据库的双写的一致性)
 
 - [redis并发竞争及解决方案](#redis并发竞争及解决方案)
 
 - [redis分布式锁](#redis分布式锁)
 
 - [redis集群分片原理](#redis集群分片原理)
 
 - [生产环境的redis是怎么部署的](#生产环境的redis是怎么部署的)
 
 
 
 
 
 
 
 
 ### 数据类型
 
     string  最大存储容量是512M
     hash    最大存储2^32-1,适合存对象
     set     最大存储2^32-1,自动去重
     list    最大存储2^32-1,文章评论列表、粉丝，通过lrange实现分页
     zset    最大存储2^32-1,排序去重,可以通过zrevrange key 0 3查询前三名，zbank key返回排名
     
 ### Redis三大利器
     
     缓存、降级、限流
     
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
 
 
 ### Redis和Memcached的区别
 
     1、redis是单线程的(避免多线程的轮询切换，非阻塞IO多路复用+纯内存事件处理器)，memcached是多线程非阻塞IO复用的网络模型。
     2、redis支持更丰富的数据类型（支持更复杂的应用场景）：Redis不仅仅支持简单的k/v类型的数据，同时还提供list，set，zset，
        hash等数据结构的存储。memcache支持简单的数据类型，String，支持更多的复杂场景。
     3、Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用,而Memecache把数据全部存在内存
        之中。
     4、redis目前官方支持redis cluster集群模式，memcached没有原生的集群模式，需要依赖客户端往集群分片中写入数据。 
     
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
 
 ### 宕机
    
      1、sdown主观宕机，及一个哨兵节点认为master node宕了
      2、odown客观宕机，及多个哨兵节点认为master node宕了
         sdown---->odown及主观宕机向客观宕机转换，通过配置quorum的参数值，如果quorum个哨兵认为master node节点宕了那么
         就会转换客观宕机
 
 ### redis集群模式
     
                     client
                       |
                       |
                      master(写)------(从master复制到slave)------slave(读)
                       |
                       |
                      sentinal cluster
                      缺点：单master承载数据量有限
                  通过redis-cluster（多master+读写分离+高可用）
                      master(写)------(从master复制到slave)------slave(读)
                       |
                       |
                       |
                      master(写)------(从master复制到slave)------slave(读)
                       |
                       |
                      sentinal cluster
                  redis cluster  VS  replication(读写分离)+sentinal(哨兵)
                      replication+sentinal适合数据量比较少
                      redis cluster适合海量数据、高并发、高可用             
 
 ### 分布式数据存储的核心算法
    
     下面以3个master节点为例：
     hash算法--->一致性hash算法(memcached)------>redis cluster,hash slot算法
     解决问题：解决了在多个master节点的时候数据如何分布到这些节点上去。
              1、hash算法：
                     hash值取模运算，假如目前有3个master节点但是当一个master宕机了之后利用hash算法将会重新对剩下2台服务
                     进行取模运算，此时就会导致大量的取模后的运算所落到master节点的位置不同，就会使大量缓存失效接近100%的
                     缓存失效就会直接请求数据库，在高并发中大量请求数据库导致数据库压垮的情况。
              2、一致性hash算法：
                   一致性hash算法也是对hash取模运算，只不过是3个master节点分布在一个圆环上，并且是hash顺时针取模运算看运算
                   之后的值与圆环上哪个节点最近，最近的话就取那个节点，这样的话假如有一台master节点宕了就不会就不会和hash算
                   法那样对所有节点都重新取模运算，而是只对宕了的那台进行hash运算之后按顺时针看离哪个master节点最近把数据打
                   到那个节点上，此时就相当于有1/3的缓存数据失效会直接请求数据库。
                   缓存热点问题：
                      就会导致某个master节点的请求比较多，导致master节点性能问题。
                      解决：一致性hash虚拟节点算法（给每个mater都做了均匀的虚拟节点，这样即使某个节点挂了也不会导致某个节
                           点热点的问题）。
                 3、hash slot算法：
                     redis cluster有固定的16384个hash slot,对每个key计算CRC16值，然后对16384取模，可以找到对应的
                     hash slot,因为hash slot是均匀的分配到每个master节点上，即使有一个master节点宕了，此时也会和一致性
                     hash算法一样，将会有1/3的请求直接请求数据库，此时分布在宕了的master节点上的hash slot就会均匀的迁移
                     到正常的master节点上。
 
 ### 缓存穿透与缓存击穿与缓存雪崩
    
     缓存穿透：
            缓存穿透现象：大量访问缓存中及数据库中都不存在的请求，导致大量请求会直接请求数据库导致服务挂掉。
            解决：有很多种方法可以有效的解决缓存穿透的问题，最常见的就是采用布隆过滤器，将所有可能存在的数据哈希到一个足够大的
                 bitmap中，一个一定不存在的数据会被这个bitmap拦截掉，从而避免了对底层存储系统的查询压力。另外也有一个更为简
                 单粗暴的方法，如果一个查询返回的数据为空，我们仍把这个空结果进行缓存，但它的过期时间会很短，最好不要超过5分
                 钟。
     缓存击穿：
            对于一个设置了过期时间的key，这个key可能会在某些时间点被超高并发地访问，是一种非常“热点”的数据。这个时候，需要
            考虑一个问题：缓存被“击穿”的问题，这个和缓存雪崩的区别在于这里针对某一key缓存，前者则是很多key。
            解决:使用互斥锁(mutex key)
                业界比较常用的做法，是使用mutex。简单地来说，就是在缓存失效的时候（判断拿出来的值为空），不是立即去load db，
                而是先使用缓存工具的某些带成功操作返回值的操作（比如Redis的SETNX或者Memcache的ADD）去set一个mutex key，
                当操作返回成功时，再进行load db的操作并回设缓存；否则，就重试整个get缓存的方法。
                  
     缓存雪崩：
            缓存雪崩现象：对于多个设置了过期时间的key，这些key可能会在某些时间点被超高并发地访问，是一种非常“热点”的数据。
            缓存服务器挂了，导致大量请求直接请求数据库导致，数据库支撑不住。
            解决：
                事前：用redis cluster高可用架构
                事中：做多级缓存就是在redis上层加一层ehcache,用户请求的时候先从ehcache中取，如果ehcache中没有则去redis
                     中去取，如果redis中没有再去数据库中去取。限流处理(hystrix)加一层hystrix处理，如果此时高并发中流量为
                     5000并且redis挂了，可以设置hystrix为2000，当访问的时候先从ehcache中去取，如果没有再请求数据库，剩余
                     3000设置友情提示或者空白页等。不至于服务宕机不能用。
                事后：redis持久化机制，redis服务好了之后可以恢复正常。
                处理：可以让多个key失效不在同一个时间比如我们可以在原有的失效时间基础上增加一个随机值，比如1-5分钟随机，这样
                     每一个缓存的过期时间的重复率就会降低，就很难引发集体失效的事件。
 
 ### 保证缓存与数据库的双写的一致性
    
     cache aside pattern
         1、当服务访问数据查询的时候先去访问redis缓存，如果redis缓存没有的话再去访问数据库，然后把数据缓存到redis中。
         2、当访问访问数据更新的时候先把redis中缓存的数据删了，然后再修改数据库中的数据，然后再缓存到redis中。
            为什么是删除缓存而不是更新缓存：因为现实中会存在复杂的缓存的场景，不单是从数据库中直接取出来的数据。
            场景：库存
                 1、当修改数据的时候，就会先修改数据库，然后再删除缓存此时如果缓存还没有删除此时由于网络原因超时等，当服务好了
                    的话就会导致数据库数据和redis缓存数据不一致的情况及双写不一致的情况。
                    解决：先删除缓存再修改数据库。
                 2、在高并发的情况下（假如库存100）
                     同时有两个请求，1个请求读库存100想要减1库存及改为99及就会先把redis的缓存库存给删除，再修改数据库库存但
                     是此时还没有修改，另外一个请求读库存及先到redis中读此时redis中库存已经被删除了，那么就会读取
                     数据库中的库存此时库存还是100，此时读取到的数据就不一致了。
                     解决：加一个内存队列，及第一个修改库存请求先放到队列中然后第二个读请求放到这个队列之后，那么只有第一个请
                          求处理完之后才会处理第二个请求。
 
 ### redis并发竞争及解决方案
     
     期望场景：set test_key v1-->v2-->v3--v4
     现实场景：set test_key v1-->v3-->v4--v2
     解决：分布式锁（zookeeper）---加时间戳
 
 ### redis分布式锁
 
     1、普通实现：
          set orderId:1:lock 随机值 NX PX 30000
          释放锁通过lua脚本会判断一下orderId:1:lock及key的随机值，如果随机值不一致则不能删除
          问题：
             单机的话挂了就完了。
             主备也会存在分布式锁失败的问题（当master宕了还没来得及切换到slave就会导致事务不一致问题）
     2、RedLock算法
          redis cluster集群模式
          多个master分别获取锁，大多数node节点获取锁的时候才能针对某一个进行加锁，保证了即使宕机也会保证数据的一致性。
     对比：
         redis锁依赖与集群，每个一段时间尝试获取锁造成性能开销
         zookeeper是通过消息监听的方式，相对redis分布式锁要好
 
 ### redis集群分片原理
    
     Redis集群分片是把一块数据分片保存到多个机器，可以横向扩展数据库大小、扩展带宽，计算能力等。
     	实现数据集群分片大致有三种
     	1、在客户端实现数据集群分片
     	2、在服务器端实现数据集群分片
     	3、通过代理服务器实现集群分片
              
 ### 生产环境的redis是怎么部署的
    
     redis cluster
     部署10台机器，5台主实例提供写服务，5台从实例提供读服务，每个节点的读写高峰可达QPS 5W,5台机器最多读写请求25W/s;机器是32G
     内存+8核CPU,分配给redis进程有10G内存。
 
 
 
 
 
 
 
 
 
 
 
 
     

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆