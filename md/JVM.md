# JVM相关

 - [类加载机制的生命周期](#类加载机制的生命周期)
 
 - [类加载器](#类加载器)
 
 - [垃圾收集器](#垃圾收集器)
 
 - [GC Roots](#GC Roots)
 
 - [运行时数据区](#运行时数据区)
 
 - [JDK1.8为什么废弃永久代](#JDK1.8为什么废弃永久代)
 
 
 ### 类加载机制的生命周期
 
     1、加载（将类的class文件加载到内存中）
     2、验证（验证class对象是否符合jvm的要求，及是否对jvm产生危害）
     3、准备（将静态变量加载到内存中）
     4、解析（将符号引用替换成直接引用）
     5、初始化（将类的静态变量及静态方法进行初始化）
  
 ### 类加载器
 
     BootStrapClassLoader(启动类加载器/根加载器)
     ExtensionClassLoader(扩展加载器)
     ApplicationClassLoader(应用程序加载器)
     UserClassLoader(用户自定义加载器)   
     
 ### 垃圾收集器
 
     Serial:单线程版
     ParNew:Serial的多线程版
     CMS:采用标记-清除算法(会产生大量碎片)
         Concurrent Mark Sweep Collector是回收停顿时间比较短，目前比较常用的垃圾回收器。
         步骤：
            1、初始标记(Initial Mark)
            2、并发标记(Concurrent Mark)
            3、重新标记(Remark)
            4、并发清除(Concurrent Sweep)
         第1、3步的初始标记和重新标记阶段会引发STW，而第2、4步的并发标记及并发清除两个阶段和应用程序
         并发执行，也是比较耗时的操作，但并不影响应用程序的正常执行。由于CMS采用的是"标记-清除算法"，
         因此产生大量的空间碎片。为了解决这个问题，CMS通过配置-XX:+UseCMSCompactAtFullCollection
         参数，强制JVM在FGC完成后对老年代进行压缩，执行一次空间碎片整理，但是空间碎片整理阶段也会引发
         STW。为了减少STW次数，CMS还可以通过配置-XX:+CMSFullGCsBeforeCompaction=n参数，在执行了
         n次FGC后，JVM再在老年代执行空间碎片整理。
            
     G1:分代回收算法(新生代采用复制算法；老年代采用标记清除算法)
 
 ### GC Roots    
     可达性分析法
     可作为GC Roots的对象包括下面几种：
     1、虚拟机栈(栈针中的本地变量表)中引用的对象
     2、方法区中类静态属性引用的对象。
     3、方法区中常量引用的对象。
     4、本地方法栈中JNI(即一般说的Native方法)方法引用的对象。
 
 ### 运行时数据区
     
     1、程序计数器(线程私有，在多线程执行过程中由于CPU轮询切片而记录执行位置)
     2、java虚拟机栈(线程私有，存储基本数据类型对象引用)
     3、本地方法栈(线程私有，Native方法)
     4、java堆(线程共享，java虚拟机中最大的一块，垃圾收集器管理的主要区域)
     5、方法区(线程共享，存储被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据)
     6、运行时常量池(用于存放编译期生成的各种字面量和符号引用)
     7、直接内存    
     
 ### JDK1.8为什么废弃永久代
     
     官方回答：This is part of the JRockit and Hotspot convergence effort. JRockit 
              customers do not need to configure the permanent generation (since JRockit
              does not have a permanent generation) and are accustomed to not configuring
              the permanent generation.
        
     移除永久代是为融合HotSpot JVM与 JRockit VM而做出的努力，因为JRockit没有永久代，不需要配置永久代。
     现实使用中易出问题:
        由于永久代内存经常不够用或发生内存泄露，爆出异常java.lang.OutOfMemoryError: PermGen
        

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆