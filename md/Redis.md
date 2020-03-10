# Redis相关

 - [数据类型](#数据类型)
 
 - [Redis持久化方式](#Redis持久化方式)
 
 - [使用redis实现限制前台点击](#使用redis实现限制前台点击)
 
 ### 数据类型
 
     string
     hash
     set
     list
     zset
 
 ### Redis持久化方式
  
     1、RDB：将内存中的数据缓存到磁盘中。
     2、AOF：将内存中的数据以日志追加的方式保存到文件中。

 ### 使用redis实现限制前台点击
     
     前台创建一个uuid+时间戳传给后台，调用redis的自增方法，将key中存储的数字值自增1，如果调用中key不存在，那么先把key进行初始化
     在调用redis的INCR方法，如果值包含错误的类型或者字符串类型的值不能表示为数字，那么返回一个错误。
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆