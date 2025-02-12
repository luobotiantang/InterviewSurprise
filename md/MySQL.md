# MySQL数据库

 - [JDBC连接数据库的步骤](#JDBC连接数据库的步骤)
 
 - [索引](#索引)
 
 - [事务的特性](#事务的特性)
 
 - [事务的隔离级别](#事务的隔离级别)
 
 - [存储引擎](#存储引擎)
 
 - [查询有2门及以上不及格科目的学生姓名及其平均成绩](#查询有2门及以上不及格科目的学生姓名及其平均成绩)
 
 - [SQL语句如何查询评论表里每个人的最新一条评论信息](#SQL语句如何查询评论表里每个人的最新一条评论信息)
 
 - [三个join的含义](#三个join的含义)
 
 - [truncate与delete的区别](#truncate与delete的区别)
 
 ### JDBC连接数据库的步骤
 
     1、JDBC所需的4个参数(user、password、url、driverClass);
     2、加载JDBC驱动程序；
     3、创建数据库的连接；
     4、创建一个preparedStatement；
     5、执行SQL语句；
     6、遍历结果集；
     7、处理异常，关闭JDBC对象资源；
 
 ### 索引
 
     创建联合索引：create index name_index on employee (employee_name,employee_age);
     
     索引什么情况下会生效、失效：
                           索引生效：当创建索引字段在查询或者表关联的时候就会生效，可以用explain执行计划来查看。
                                    如：explain select * from a;
                                    执行计划关键字：tyep、key、rows
                           索引失效：
                                  1、条件中用or即使其中有条件带索引也不会使用索引查询。
                                  2、like的模糊查询以%开头，索引失效。
                                  3、如果列类型是字符串一定要在查询的时候加上''不然也会引起索引失效。
                                  4、MySQL预计使用全表扫描比索引快时，则不走索引。

     MYSQL聚簇索引和非聚簇索引：
        聚簇索引:数据存储顺序和索引顺序一致，在聚簇索引中的叶子节点就是数据节点。
        非聚簇索引:叶子节点还是索引节点，只不过非聚簇索引有指向数据块的指针。
     
     MYSQL有几种索引：
        主键索引、唯一索引、普通索引、全文索引、联合索引
        
     B树和B+树的区别：
        B树:每个节点都存储key和data，叶子节点指针为null
        B+树:只有叶子节点存储行数据，叶子节点包含了这棵树的所有键值，叶子节点不存储指针
           
 ### 事务的特性
 
     原子性
     一致性
     隔离性
     持久性
     
 ### 事务的隔离级别
     
     读未提交：别人改数据的事务尚未提交，我在我的事务中也可以看到。(导致：脏读、不可重复读、幻读)
     读已提交：别人改数据的事务已经提交，我在我的事务中才可以看到。(避免:脏读； 导致:不可重复读、幻读)
              Oracle数据库默认隔离级别。
     可重复读：别人改数据的事务已经提交，我在我的事务中也不去读。  (避免:脏读、不可重复读； 导致:幻读)
              MySQL数据库默认隔离级别。
     串 行 化：我的事务尚未提交，别人就别想改数据。(应用场景:行锁)(避免:脏读、不可重复读、幻读)
     
     脏读:指一个事务正在访问数据，并进行修改但是还没有更新到数据库，此时另外一个事务读取数据，并是用了这个数据。
     不可重复读:指一个事务多次读取数据，此时有另外一个事务对数据进行修改，导致第一个事务多次读取数据不一致。
     幻读:新增或者删除的时候(一会新增一条数据，一会少一条数据)，会出现幻觉的情况。
     
     这四种隔离级别:并行性能依次降低；安全性能依次提高；
     不可重复读的重点是修改；幻读的重点是新增或者删除；
  
 ### 存储引擎
    
     InnoDB和MyISAM的区别
     1、InnoDB支持事务，MyISAM不支持事务；
     2、InnoDB支持行锁，MyISAM支持表锁；
     3、InnoDB支持外键，MyISAM不支持外键；
     4、InnoDB支持MVCC(多版本并发控制)，MyISAM不支持；
     5、InnoDB不支持全文检索，MySIAM支持全文检索；
 
 ### 查询有2门及以上不及格科目的学生姓名及其平均成绩
 
     select s.sname,avg(sc.grade) from s,sc
     where s.s#=sc.s# and sc.grade<60 group by s.sname having by count(sc.grade)>=2; 
     
 ### SQL语句如何查询评论表里每个人的最新一条评论信息
     
     select * from test group by f,t order by time desc limit 1;
     
 ### 三个join的含义
 
     left join(左连接):返回左表中的所有记录以及和右表中的连接字段相等的记录。
     right join(右连接):返回右表中所有记录以及和左表中连接字段相等的记录。
     inner join(等值连接):只返回两个表中连接字段相等的记录。    
     
 ### truncate与delete的区别
 
     truncat是DDL语句操作，delete是DML语句操作，他们的共同点都是清空表内的数据。
        1、truncat在事务中不能被回滚，delete在事务中可以回滚。
        2、truncat会清空表的自增属性，delete不会
    
     DDL(Data Definition Language)数据定义语言：
        适用范围：对数据库中的某些对象(例如，database,table)进行管理，如Create,Alter和Drop.truncate
     DML(Data Manipulation Language)数据操纵语言：
        适用范围：对数据库中的数据进行一些简单操作，如insert,delete,update,select等.
     1.DDL操作是隐性提交的，不能rollback！      
     2.DML操作是需要手动控制事务的开启、提交(commit)和回滚的。
         
     
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆