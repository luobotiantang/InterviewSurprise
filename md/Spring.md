# Spring相关

 - [Spring的两种代理JDK和CGLIB](#Spring的两种代理JDK和CGLIB)
 
 - [Spring装载Bean的方式](#Spring装载Bean的方式)
 
 - [Spring中bean的加载及获取](#Spring中bean的加载及获取)
 
 - [Spring中Bean的5种作用域](#Spring中Bean的5种作用域)
 
 
 ### Spring的两种代理JDK和CGLIB
 
     JDK：Java动态代理是利用反射机制生成一个代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
     CGLIB：CGLIB动态代理是利用asm开源包，对代理对象的class文件加载进来，通过修改字节码生成子类来处理。         
     
 ### Spring装载Bean的方式
     
     AutoWired是Spring的默认按照类型装配；Resource是JDK1.6的按照名称装配。
     
     1、@AutoWired与@Resource都可以用来装配bean,都可以写在字段上或写在setter方法上。
     2、@AutoWired默认按照类型装配默认情况下必须要求依赖对象必须存在，不存在会NullPointException，如果
        允许null值可以设置它的required属性值为false
        如：@AutoWired(required=false)
        如果我们要使用名称装配可以结合@Qualifier注解进行使用
        如：@Autowired() @Qualifier("baseDao")
           private BaseDao baseDao;
 
 ### Spring中bean的加载及获取
     
     从xml读取bean的配置信息加载到Spring容器中，通过xml配置的id从Spring容器反射得到这个类的实例对象。将
     bean存储到BeanDefinitionMap中，然后从BeanDefinitionMap获取bean;   
     
 ### Spring中Bean的5种作用域
 
     在spring中，那些组成应用程序的主体及由spring IOC容器所管理的对象，被称之为bean。简单的讲bean就是IOC
     容器初始化、装配及管理的对象，除此之外，bean就与应用程序的其他对象没有什么区别。而bean的定义以及bean相
     互间的依赖关系将通过配置元数据来描述。
     Singleton：单利模式，在整个Spring IOC容器中，spring默认的作用域。
     Prototype：原型模式
     Request
     Session
     Globalsession
         

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆