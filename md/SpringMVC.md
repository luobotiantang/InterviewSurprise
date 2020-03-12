# SpringMVC相关

 - [拦截器和过滤器](#拦截器和过滤器)
 
 
 ### 拦截器和过滤器
 
     1、Filter需要在web.xml中配置，依赖于Servlet
     2、Interceptor需要在SpringMVC中配置，依赖于框架
     3、Filter的执行顺序在Interceptor之前
     两者的本质区别：拦截器是基于java的反射机制，而过滤器是基于函数回调，从灵活性上说拦截器功能更强大，Filter能做的事情拦截器都能做，
                   而且可以在请求前及请求后做处理，比较灵活。
             
        

> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆