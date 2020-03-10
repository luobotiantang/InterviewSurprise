# Hibernate相关

 - [Hibernate一级、二级缓存](#Hibernate一级、二级缓存)
 
 
 ### Hibernate一级、二级缓存
     Hibernate的缓存包括Session的缓存和SessionFactory的缓存，其中SessionFactory的缓存又可以分为两类：内置缓存和外置缓存。
     Session的缓存是内置的，不能被卸载，也被称为Hibernate的第一级缓存。SessionFactory的内置缓存和Session的缓存在实现方式
     上比较相似，前者是SessionFactory对象的一些集合属性包含的数据，后者是指Session的一些集合属性包含的数据。SessionFactory
     的内置缓存中存放了映射元数据和预定义SQL语句，映射元数据是映射文件中数据的拷贝，而预定义SQL语句是在Hibernate初始化阶段根
     据映射元数据推导出来，SessionFactory的内置缓存是只读的，应用程序不能修改缓存中的映射元数据和预定义SQL语句，因此
     SessionFactory不需要进行内置缓存与映射文件的同步。SessionFactory的外置缓存是一个可配置的插件。在默认情况下，
     SessionFactory不会启用这个插件。外置缓存的数据是数据库数据的拷贝，外置缓存的介质可以是内存或者硬盘。SessionFactory的外
     置缓存也被称为Hibernate的第二级缓存。         
     
     内存益出原因：当你对数据库进行数量级有万级、十万级、百万级甚至千万级，Hibernate会把操作的对象全部放到自身的内部缓存来进行
                 管理。这样就消耗了大量的内存导致内存益处。
     解决方法：
            1、优化Hibernate程序上采用分段插入即时清除缓存的方法。
            2、绕过Hibernate API直接通过JDBC来做批量插入，这个方法性能上是最好的也是最快的。
            3、事务提交之后直接清除缓存。
       


> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆