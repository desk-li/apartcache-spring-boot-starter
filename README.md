# apartcache-spring-boot-starter
无代码侵入式的缓存框架

1.可用接口

(1)添加接口缓存方法

http://localhost:8080/manage/add?method=com.example.demo.service.Love.sayLove()

(2)查看指定接口方法的缓存名称

http://localhost:8080/manage/cacheName?method=com.example.demo.service.Love.sayLove()

(3)查看已配置缓存的接口方法数量

http://localhost:8080/manage/size

(4)删除接口缓存方法

http://localhost:8080/manage/remove?method=com.example.demo.service.Love.sayLove()

(5)查看所有接口缓存方法

http://localhost:8080/manage/all
