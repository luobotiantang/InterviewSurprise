# SpringBoot相关

 - [MyBatis一级、二级缓存](#MyBatis一级、二级缓存)
 
 
 ### MyBatis一级、二级缓存
     一级缓存：MyBatis的一级缓存是SQLSession级别的缓存，在操作数据库时需要构造SqlSession对象，在对象中有一个数据结构
             (HashMap)用于存储缓存数据，不同的sqlSession之间缓存的数据区域（HashMap）是互不影响的。
     二级缓存：MyBatis的二级缓存是mapper级别的缓存，多个SqlSession去操作同一个sql语句，多个SqlSession可以共用二级缓存，
              二级缓存是跨SqlSession的缓存。         
     


> reubenwang@163.com
> 没事别找我，找我也不在！--我很忙🦆