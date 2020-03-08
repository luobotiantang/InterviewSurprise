# Java基础

 - [public、protected、default、private关键字作用域](#java关键字作用域)
 
 - [如何通过反射拿到private的属性和方法](#如何通过反射拿到private的属性和方法)
 
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
     
 ### 如何通过反射拿到private的属性和方法
     AccessibleObject类是field、method和constructor对象的基类。它提供了将反射的对象标记为在使用时取消默认java语言访问的检测能力。对
     于公共成员、默认打包访问成员、受保护成员和私有成员在分别使用field、method和constructor对象来设置或获得字段、调用方法、或者创建和初
     始化类的新实例的时候，会执行访问检测。当反射对象的accessible标志设为true时则表示反射的对象在使用时应该取消java语言的访问检查，反之
     则检查。由于jdk的安全检查耗时比较多，所以通过setAccessible(true)的方式关闭安全检查来提升反射速度。
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