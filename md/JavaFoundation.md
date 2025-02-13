# Java基础

 - [public、protected、default、private关键字作用域](#java关键字作用域)
 
 - [如何通过反射拿到private的属性和方法](#如何通过反射拿到private的属性和方法)
 
 - [IO字节流和字符流](#IO字节流和字符流)
 
 - [StringBuilder和StringBuffer的区别](#StringBuilder和StringBuffer的区别)
 
 - [==和equals的区别](#==和equals的区别)
 
 - [动态代理、静态代理](#动态代理、静态代理)
 
 - [泛型被擦出](#泛型被擦出)
 
 - [单例模式](#单例模式)
 
 - [ThreadLocal](#ThreadLocal)
 
 - [Integer与int的区别](#Integer与int的区别)
 
 - [Object的equals方法具体实现](#Object的equals方法具体实现)
 
 - [BIO、NIO、AIO的理解](#BIO与NIO和AIO的理解)
  
 - [红黑树](#红黑树)
 
 - [QPS](#QPS)
 
 - [Synchronized与Lock的区别及应用](#Synchronized与Lock的区别及应用)
 
 - [Volatile关键字](#Volatile关键字)
 
 - [创建对象的方式](#创建对象的方式)
 
 - [乐观锁和悲观锁](#乐观锁和悲观锁)
 
 - [内部类对象实例化](内部类对象实例化)
 
 - [HashMap](#HashMap)
 
 - [TreeMap](#TreeMap)
 
 ### java关键字作用域
 
     注：(1：支持；0：不支持)
                当前类     同一包     子孙类     其他包
     public       1         1         1         1
     protected    1         1         1         0
     default      1         1         0         0
     private      1         0         0         0
     
 ### 如何通过反射拿到private的属性和方法
     AccessibleObject类是field、method和constructor对象的基类。它提供了将反射的对象标记为在使用时取消默认java语言访问的
     检测能力。对于公共成员、默认打包访问成员、受保护成员和私有成员在分别使用field、method和constructor对象来设置或获得字段
     、调用方法、或者创建和初始化类的新实例的时候，会执行访问检测。当反射对象的accessible标志设为true时则表示反射的对象在使用
     时应该取消java语言的访问检查，反之则检查。由于jdk的安全检查耗时比较多，所以通过setAccessible(true)的方式关闭安全检查来
     提升反射速度。
     例如：
     
     Person person = new Person();
     Class<?> personName = person.getClass();
     Field field = personName.getDeclaredField("name");
     field.setAccessible(true);
     field.set(person,"Dog");
     System.out.println("Hello: "+field .get(person));
     
     class Person{
        private String name;
        public person(){}
        public String getName(){
            return name;
        }
     }
        
 ### IO字节流和字符流
 
     字节流的抽象流：InputStream、OutputStream
     字符流的抽象流：Reader、Writer
     区别：
        1、字节流操作的基本单元为字节，字符流操作的基本单元为Unicode码元
        2、字节流默认不实用缓冲区，字符流使用缓冲区
        3、字节流通常用于处理二进制数据，实际上它可以处理任意类型的数据，但它不支持直接写入或读取Unicode码元；字符流通常处理文
           本数据，它支持写入及读取Unicode码元。
           
 ### StringBuilder和StringBuffer的区别
 
     相同点：StringBuilder和StringBuffer都是字符串可变的；
     不同点：StringBuilder是线程不安全的，适用于单线程下在字符缓冲区进行大量操作的情况；StringBuffer是线程安全的适用于在多线
            程对字符缓冲区进行大量操作的情况。
            
 ### ==和equals的区别
 
     ==:基本数据类型比较的是值；引用数据类型比较的是内存地址
     equals:类没有复写equals()方法，等价于==比较的是内存地址；复写equals方法比较的是对象的内容；
     
 ### 动态代理、静态代理
 
     区别：就拿租房子为例（房主、中介、房客）为例，静态代理是房主有几套房子就委托几个中介租给房子；
          动态代理是房主把好几套房子都委托给同一个中介出租房子。
     为了解决每有一个代理类就需要创建一个代理类的弊端，就出现了动态代理，动态代理就是把所有东西都抽象出来。
     动态代理应用场景：
                    1、AOP
                    2、自定义第三方类库的某些方法。
 
 ### 泛型被擦出
 
     泛型在编译的时候就被擦除
     虚拟机中没有泛型类型的对象，所有对象都是普通的类，无论我们在什么时候定义泛型类型，在虚拟机中都自动转换成了一个相应的原始类型
     ，原始类型就是擦除类型变量。
     泛型被擦除的规则：
        泛型擦除就是类型变量用第一个限定来替换，如果没有限定就用Object替换。    

 ### 单例模式
 
     双重检查锁的懒加载机制
     public class SingleTon{
        private static volitale SingleTon singleTon;
        public void SingleTon(){}
        public static SingleTon getInstance(){
            if(singleTon==null){
                synchronized(SingleTon.class){
                    if(singleTon==null){
                        //singleTon用volitale修饰是为了防止指令重排，如果不加volitale那么2，3将会指令重排导致对象还没
                          初始化就分配内存空间。
                        //1、分配内存空间
                        //2、对象初始化
                        //3、为初始化对象分配内存空间
                        singleTon = new SingleTon();
                    }
                }
            }
            return singleTon;
        }
     }
     
 ### ThreadLocal
 
     ThreadLocal是干什么的？其实ThreadLocal就是一个工具类
     每一个ThreadLocal只保存一个键值对，并且各个线程的数据互不干扰，每个线程都有一个ThreadLocalMap数据结构，当执行set方法时
     其值是保存当前线程的threadLocals变量中，当执行get方法时是从当前线程的threadLocals中取，所以各个线程的数据互不干扰。
     简单的说ThreadLocal就是一种以空间换时间的做法，在每个Thread里面维护了一个以开地址法实现的ThreadLocal.ThreadLocalMap
     把数据进行隔离。
        1、隔离数据值
        2、保证每个变量都可以修改各自变量的副本
     ThreadLocalMap<ThreadLocal,Object>的弱引用问题？
        ThreadLocalMap<null,Object>的键值对为空的情况，会导致内存溢出(ThreadLocal被回收，ThreadLocal关联的共享变量还在)。
        解决：
            1、使用完线程共享变量之后，调用ThreadLocal.remove()方法清除线程共享变量。
            2、JDK建议将ThreadLocal设置成private static类型，这样ThreadLocal的弱引用问题就不存在了。
 
 ### Integer与int的区别
     这个题面试会问接口传参的问题，用Integer因为可以判断是否空传的情况；还有就是整型字面量范围[-128~127]
     1、Integer是int提供的封装类，int是java的基本数据类型；
     2、Integer的默认值是null而int的默认值是0；
     3、声明为Integer的变量需要实例化，而声明int的变量不需要实例化；
  
 ### Object的equals方法具体实现
 
     public boolean equals(Object obj){
        return (this == obj);
     }
     就是比较当前对象和对比的对象的内存地址是否相等及是否在同一内存区域。 
     
 ### BIO与NIO和AIO的理解
     
     BIO:同步阻塞IO,数据的读取及写入必须阻塞到一个线程内等待其完成。
     NIO:同步非阻塞IO，提供了Channel、Selector、Buffer等的抽象。
     AIO:异步非阻塞IO，基于事件和回调机制实现。
     
     要从NIO流是非阻塞IO而IO流是阻塞IO说起，然后可以从NIO的3个核心组建特性为NIO带来的一些改进进行分析。
     NIO：
        java NIO使我们可以进行非阻塞IO操作。比如说单线程从通道里读取数据到buffer，同时可以继续处理别的事情；写数据也是一样，
        及一个线程写入数据到通道，但不需要等待完全写入，这个线程同时可以去做别的事情。
        
        1、Buffer(缓冲区)
           IO面向流(InputStream/OutputStream)，而NIO面向缓冲区(Buffer)在IO中可以直接将数据写入或者读取数据到stream对
           象中NIO是直接将数据读取或者将数据写入到Buffer中。
           虽然stram中也有buffer开头的扩展类，但只是流的包装类，还是将数据读到stream然后将流读到缓冲区，而NIO直接读取数据
           到缓冲区。
           最常用的缓冲区是ByteBuffer,一个ByteBuffer提供了一组功能用于操作byte数组。除了ByteBuffer还有其他缓冲区，事实
           上每一种java基本类型(除Bollean类型)都对应有一种缓冲区。
           
        2、Channel(通道)
           NIO通过Channel通道进行读写，通道是双向的可以读也可以写，而流的读写是单向的，无论读写通道只能和buffer交互。因为
           Buffer通道可以异步的读写。
           
        3、Selector(选择器)
           NIO有选择器而IO没有。
           选择器用于使用单个线程处理多个通道。因此它需要较少的线程处理这些通道。线程之间的切换对于操作系统来说是昂贵的。因
           此选择器可以提高系统故障的效率。
          
     IO:
        java IO的各种IO操作是阻塞的。这意味着当一个线程调用read()和write()方法时，该线程被阻塞，直到有一些数据被读取，或
        者数据完全写入，该线程在此期间不能在做其他操作了。
     AIO:
        AIO也就是NIO2.0在java7中引入了NIO的改进版NIO 2,它是异步非阻塞的IO模型。异步IO是基于事件及回调机制实现的，就是应
        用在操作之后不会立刻返回，也不会阻塞到那里，当后台处理完成，操作系统会通知相应的进程进行后续的操作。
 
 ### 红黑树
     
     有红必有黑，红红不相连。
     根节点为黑色
     叶子节点为黑色
     叶子节点的NIL节点也为黑色
     两个相邻节点之间不能都为红色
 
 ### QPS
 
     原理:每天80%的访问集中在20%的时间里，这20%时间叫做峰值时间
     公式:(总PV数 * 80%)/(每天秒数 * 20%)=峰值时间每秒请求数(QPS)    
     机器:峰值时间每秒QPS/单台机器的QPS=需要的机器数
     每天300W PV的访问在单台机器上，这台机器需要多少QPS？
     (3000000 *0.8)/(86400*0.2)=139(QPS)
     一般要达到139QPS，因为是峰值
        
 ### Synchronized与Lock的区别及应用
     Synchronized:
        Synchronized应用悲观锁，Synchronized底层是monitor监视器，JVM是通过monitor.enter、monitor.exit(进入、退出)
        对象监视器对方法和代码块的同步的。编译之后会在同步代码块之前执行monitor.enter指令，调用代码块之后执行monitor.exit
        指令，没有获取到锁的线程会阻塞到方法的入口处，直到获取锁的线程monitor.exit之后其他才可以继续执行。
            
 ### Volatile关键字
     
     可见性、顺序性
     注:不能保证原子性
 
 ### 创建对象的方式
     1、new
     2、反射
     3、clone
     4、动态代理    
 
 ### 乐观锁和悲观锁
    
     悲观锁：
     	悲观锁故名思义就是很悲观，每次去拿数据的时候都认为别人会修改，所以在每次去拿数据的时候就会加上锁
     	，这样别人想拿这个数据就会处于block(阻塞)状态。传统的关系型数据库里面就用到了这种锁机制，如(行锁，表锁)
     乐观锁：
     	乐观锁故名思义就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会加锁。但是在更新的时候会
     	判断在此之前有没有人去更新数据，可以使用版本号机制。
     	乐观锁大多数是基于数据库版本(Version)记录机制体现。何为数据版本？及为数据增加一个版本标识，在基于数据库
     	表的版本解决方案中，一般是通过数据库表增加一个"version"字段来实现。
     	读取数据时将此版本号一同读出，之后更新时对此版本号加1，此时将提交数据的版本数据与数据库表对应记录的当前
     	版本信息进行比对。如果提交的数据版本号大于数据库表当前版本号，则给予更新否则认为是过期数据。
     	(注意：在并发过程中，如果多条数据同时更新，只要有更新版本加1，其他数据版本也加1但是还是被认为是过期数据)       
 
 ### 内部类对象实例化
     
     public class A {
         private int id = 1;
     
         public class B {
             public void print_in(){
                 System.out.println("这是内部类的方法");
             }
             public void getId(){
                 // 内部类可以访问外部类的属性和方法
                 System.out.println("id：" + id);
             }
         }
     
         public static void main(String[] args) {
             // 实例化外部类
             A aa = new A();
             // 实例化内部类
             A.B bb = aa.new B();
         }
     }
            
 ### HashMap
     键、值都允许为空？
        当key为null时，hash值的默认值为0，只有一个key为null
        value:不需要唯一可以多个为null
     HashMap在多线程扩容的过程中为什么会形成死循环？
        如果两个线程同时触发扩容，在移动节点时会导致一个链表中的2个节点相互引用，从而生成环链表。
     不保证有序性？
        插入顺序与存储顺序不同
        插入顺序:用户操作的顺序
        存储顺序:根据hash计算得到的数组下标顺序
     HashMap底层是数组加链表，初始容量是16，负载因子是0.75,为了减少冲突的概率，当HashMap的数组长度到了一个临界值就会
     发生扩容，及默认情况下是16*0.75=12时，就会发生扩容。
     这个0.75是综合时间和空间上进行考虑的一个合适值。
     JDK7: 数组+链表；采用链地址法+头插法容易出现逆序且多线程中形成链表死循环的问题。
     JDK8及之后: 数组+链表+红黑树(节点数>=8） &&桶的总个数（table的容量）>= 64) 时，会将链表结构变成红黑树。
                链地址法+尾插法
     HashMap初始容量为什么是2的n次幂及扩容为什么是2倍的形式?
         HashMap计算添加元素的位置时，使用的位运算，这是特别高效的运算；
         另外，HashMap的初始容量是2的n次幂，扩容也是2倍的形式进行扩容，是因为容量是2的n次幂，可以使得添加的元素均匀分
         布在HashMap中的数组上，减少hash碰撞，避免形成链表的结构，使得查询效率降低！
     为什么HashMap中String、Integer这样的包装类适合做为key键：
         String、Ingteger等包装类的特性保证了Hash值的不可更改性和计算准确性，有效的减少了hash碰撞的几率。
     HashMap解决hash冲突的方法？
         开放地址法: 当hash冲突时，使用探测的方式在整个数组中找到另一个可以存储的值。
                    弊端:
                        首先在大数据集合、hash冲突经常发生的场合，如果使用开放定址法会造成在删除是重新为后续连续元素重
                        新计算地址并分配。而且由于元素的添加顺序不一致，会导致在寻找一个元素时需要跨越多个hashcode值才
                        能找到.
         链地址法:   链地址法内部仍然有一个数组，但区别与开放地址法，该数组存储的是一个链表的引用。
                        当根据hashCode计算出数组下表后，对元素的增删查改都是在该数组元素所指向的链表上完成的。这就解决
                        了hashCode重复的问题。因为，当hashCode重复，多个元素对应同一个地址，但元素实际存储的位置在数组
                        对应的链表上。所以相同hashCode的不同元素可以存储在同一位置。 
                         
 ### TreeMap
    
     为什么会出现TreeMap,因为TreeMap的存储结构是平衡二叉树，也称为红黑树。
     HashMap通过hashcode对其内容进行快速查找，而 TreeMap中所有的元素都保持着某种固定的顺序，如果你需要得到一个
     有序的结果你就应该使用TreeMap。HashMap通常比TreeMap效率要高一些，一个是哈希表，一个是二叉树，建议多使用
     HashMap，在需要排序的Map时候才用TreeMap。 
     
 
     
   
   
   
   
   
   
   
   
   
   
   
   
   
        
     
     
     
     
     
     
          
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆