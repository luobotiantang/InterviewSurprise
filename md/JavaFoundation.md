# Java基础

 - [public、protected、default、private关键字作用域](#java关键字作用域)
 
 - [如何通过反射拿到private的属性和方法](#如何通过反射拿到private的属性和方法)
 
 - [IO字节流和字符流](#IO字节流和字符流)
 
 - [StringBuilder和StringBuffer的区别](#StringBuilder和StringBuffer的区别)
 
 - [==和equals的区别](#==和equals的区别)
 
 - [动态代理、静态代理](#动态代理、静态代理)
 
 - [泛型被擦出](#泛型被擦出)
 
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
     
 ### 动态代理、静态代理
 
     区别：就拿租房子为例（房主、中介、房客）为例，静态代理是房主有几套房子就委托几个中介租给房子；
          动态代理是房主把好几套房子都委托给同一个中介出租房子。
     为了解决每有一个代理类就需要创建一个代理类的弊端，就出现了动态代理，动态代理就是把所有东西都抽象出来。
     动态代理应用场景：
                    1、AOP
                    2、自定义第三方类库的某些方法。
 
 ### 泛型被擦出
     泛型在编译的时候就被擦除
     虚拟机中没有泛型类型的对象，所有对象都是普通的类，无论我们在什么时候定义泛型类型，在虚拟机中都自动转换成了一个相应的原始类型，
     原始类型就是擦除类型变量。
     泛型被擦除的规则：
        泛型擦除就是类型变量用第一个限定来替换，如果没有限定就用Object替换。    

> reubenwang@163.com
> 没事别找我，找我也不在！--我很忙🦆