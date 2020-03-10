# SpringBoot相关

 - [SpringBoot的启动方式](#SpringBoot的启动方式)
 
 - [SpringBoot如何不用默认的tomcat](#SpringBoot如何不用默认的tomcat)
 
 ### SpringBoot的启动方式
     
     1、IDEA运行application这个类的main方法 SpringApplication.run(MySpringApplication.class,args)        
     2、在SpringBoot的应用的根目录下运行   mvn spring-boot:run
     3、使用mvn install 生成jar后运行
        先到项目根目录下
        mvn install
        cd target
        java -jar myspringboot-0.0.1-SNAPSHOT.jar

 ### SpringBoot如何不用默认的tomcat
    
     修改依赖，修改ApplicationContext的启动方法
     <dependency>
        <groupId>org.springframework.boot<groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
        </exclusions>
     </dependency>
     
     public class MyManagerWebStartApplication extends SpringBootServletInitializer{
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
            return builder.sources(MyManagerWebStartApplication.class);
        }
     }
     
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆