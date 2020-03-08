# Java基础

 - [public、protected、default、private关键字作用域](#java关键字作用域)
 
 - [如何通过反射拿到private的属性和方法](#如何通过反射拿到private的属性和方法)
 
 - [IO字节流和字符流](#IO字节流和字符流)
 
 - [StringBuilder和StringBuffer的区别](#StringBuilder和StringBuffer的区别)
 
 - [==和equals的区别](#==和equals的区别)
 
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
     
 ### 开闭原则
     Open-Closed Principle,简称OCP
     对扩展开放，对修改关闭


> reubenwang@163.com
> 没事别找我，找我也不在！--我很忙🦆