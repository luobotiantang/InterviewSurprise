# Java基础

 - [public、protected、default、private关键字作用域](#java关键字作用域)
 
 - [依赖倒置原则](#依赖倒置原则)
 
 - [接口隔离原则](#接口隔离原则)
 
 - [迪米特法则](#迪米特法则)
 
 - [开闭原则](#开闭原则)
 
 ### java关键字作用域
 
     注：(1：支持；0：不支持)
                当前类     同一包     子孙类     其他包
     public       1         1         1         1
     protected    1         1         1         0
     default      1         1         0         0
     private      1         0         0         0
     
 ### 里氏替换原则
     Liskov SubstitutionPrinciple，简称LSP
     可以理解为继承的关系
 ### 依赖倒置原则
     Dependence Inversion Principle,简称DIP
     1、高层模块不应该依赖底层模块，两者都应该依赖其抽象
     2、抽象不依赖于细节
     3、细节应该依赖于抽象
 ### 接口隔离原则
     Interface Segregation Principle,简称ISP
     类与类之间的依赖应当建立在最小的接口上
 ### 迪米特法则
     Law of Demeter,简称LoD
     最少知识原则，即一个对象应当对其他对象尽可能少的了解。
 ### 开闭原则
     Open-Closed Principle,简称OCP
     对扩展开放，对修改关闭


> reubenwang@163.com
> 没事别找我，找我也不在！--我很忙🦆