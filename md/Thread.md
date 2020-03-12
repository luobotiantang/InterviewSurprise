# 线程相关

 - [为什么要使用线程池](#为什么要使用线程池)
 
 - [创建线程池的方式](#创建线程池的方式)
 
 - [线程池参数](#线程池参数)
 
 - [CPU密集型及IO密集型如何配置线程池](#CPU密集型及IO密集型如何配置线程池)
 
 
 ### 为什么要使用线程池
 
     避免频繁的创建和销毁线程，达到线程对象的重用。另外使用线程池还可以根据项目灵活的控制并发的数目。

 ### 创建线程池的方式
 
     1、Executors.newWorkStealingPool(); JDK8引入，创建足够多的线程，并通过多个队列减少竞争，不会存在OOM的风险。
                                         下面👇四个都是无界队列当瞬间请求比较大时会出现OOM的风险。
                                         注：工作窃取及当前线程执行完之后窃取其他线程的任务。
                                         
     2、Executors.newCachedThreadPool(); 创建可伸缩线程的线程池，最大线程数Integer.MAX_VALUE,当达到最大值会OOM
                                         线程存活时间一般是60s当工作线程空闲时间大于60s的时候会回收工作线程。
                                         
     3、Executors.newScheduledThreadPool()；创建可伸缩线程的线程池，最大Integer.MAX_VALUE,当达到最大值时会OOM,
                                            newScheduledThreadPool()与newCachedThreadPool()的最大不同是不
                                            回收工作线程程。
     
     4、Executors.newSingleThreadExecutor();创建单个线程的线程池。
     
     5、Executors.newFixedThreadPool(10); 创建固定个数的线程池，不存在空闲线程，keepAliveTime=0。                                                                                                               
                                         
 ### 线程池参数
     解释一下：《码出高效》对阻塞队列解释的有些问题，下面已经回答的明明白白
     以ThreadPoolExecutor的构造函数来讲解说明
     public ThreadPoolExecutor(int corePoolSize,
                                   int maximumPoolSize,
                                   long keepAliveTime,
                                   TimeUtil unit,
                                   BlockingQueue<Runnable> woekQueue,
                                   ThreadFactory threadFactory,
                                   RejectExecutorHandler handler)
     1、corePoolSize:核心线程池数，顺带说一下核心线程池设置太大会浪费资源，但是设置太小又会带来频繁创建线程导致的性能问题，
                     这个需要考虑。
     2、maximumPoolSize:最大线程池数，当处理请求的任务数超过核心线程数的时候，其他任务就会进入blockingQueue阻塞队列中，
                        阻塞队列分有界和无界，
                        1、无缓存队列(SynchrousQueue),如果任务数大于核心线程池数，那么剩余的任务就会重新起线程执行，
                           当运行的线程数大于最大线程池数的时候，其余任务直接拒绝。
                        2、有界队列(ArrayBlockingQueue)如果任务数大于核心线程池数并且剩余的任务数大于有界队列，那么
                           就会重新新起线程，当任务超过最大线程池数的时候就会走拒绝策略。
                        3、无界队列(LinkedBlockingQueue)，如果任务数大于核心线程池数那么此时多余的任务就会放到阻塞队
                           列中，此时不会重新新起线程最大线程池数不起作用。
                           核心线程池数(corePoolSize)与最大线程池数(maximumPoolSize)相等的时候此时就会创建固定个数
                           的线程池，但是必须满足maximumPoolSize>0。
     3、keepAliveTime:限制线程存活时间，当线程池的个数大于核心线程池个数(maximumPoolSize)且线程存活时间大于
                      keepAliveTime的值的时候线程销毁直到线程池个数大于核心线程池个数为止，核心线程池个数也可以指定销毁，
                      即设置allowThreadTimeOut,当设置之后核心线程也可以销毁。当创建固定个数的线程池的方式时
                      keepAliveTime=0。
     4、util:线程存活时间单位，通常TimeUnit.SECOND
     5、workQueue:阻塞队列，当请求的任务数大于核心线程池数的时候，会把多余的任务放入阻塞队列。
     6、threadFactory:线程工厂即为相同的一组任务创建指定的线程。
     7、handler:阻绝策略即当缓存队列中线程数已经满的情况做的处理，起到限流的作用，通常有以下三种
                1、把多余的请求存到数据库中，起到削峰的作用，当线程空闲的时候再从数据库中取出。
                2、跳转到指定页面。
                3、日志记录。
     
 ### CPU密集型及IO密集型如何配置线程池
    
     CPU密集型:线程数=CPU核数
     IO密集型:线程数通常是CPU核数的两倍
     总结:线程最佳数量=(线程等待时间+线程CPU时间)/线程CPU时间 * CPU核数
     在开发中我们经常会使用线程池来处理一些业务，而在不新增设备的情况下，我们使用的线程资源又是有限的。
        1、高并发、任务执行时间短的业务怎样使用线程池？
            线程数=CPU核数+1  避免线程的上下文切换
        2、并发不高、任务执行时间长的业务怎样使用线程池？
            需要考虑任务执行时间长由于IO操作时间长还是由于计算操作时间长，如果是IO操作时间长(线程数可以相对增加),如果是
            计算时间长(线程数可以设置CPU核数+1,避免线程上下文切换)   
        3、并发高、业务执行时间长的业务怎样使用线程池？
            这个时候解决问题不能单靠线程池中线程的个数，需要从整体架构上考虑，适当的增加缓存机制redis,还是不行的话加机器
            ，如果任务执行时间过长的话可以考虑拆分任务到队列中，可以考虑中间件如RocketMQ；
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
                                           
    
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆