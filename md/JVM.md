# JVM相关

 - [类加载机制的生命周期](#类加载机制的生命周期)
 
 - [类加载器](#类加载器)
 
 - [垃圾收集器](#垃圾收集器)
 
 
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
     G1:分代回收算法(新生代采用复制算法；老年代采用标记清除算法)
     
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆