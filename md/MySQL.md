# MySQL数据库

 - [索引](#索引)
 
 
 ### 索引
 
     创建联合索引：create index name_index on employee (employee_name,employee_age);
     
     索引什么情况下会生效、失效：
                           索引生效：当创建索引字段在查询或者表关联的时候就会生效，可以用explain执行计划来查看。
                                    如：explain select * from a;
                           索引失效：
                                  1、条件中用or即使其中有条件带索引也不会使用索引查询。
                                  2、like的模糊查询以%开头，索引失效。
                                  3、如果列类型是字符串一定要在查询的时候加上''不然也会引起索引失效。
                                  4、MySQL预计使用全表扫描比索引快时，则不走索引。


> reubenwang@163.com
> 没事别找我，找我也不在！--我很忙🦆