# spring-study

## 사용기술
### 백엔드
#### SpringBoot
- JAVA 8
- Spring Security
- Spring Validation
- Spring Security OAuth2
- Spring MVC
- Spring AOP
- JPA(Hibernate)
- Querydsl
- Lombok
- Spring Websocket
- Stomp

#### Build tool
- Gradle

#### DataBase
- Mysql
- Redis

### 프론트엔드
- JavaScript
- JSP
- JQuery


## Docker Redis

  
```
docker network create redis-net

docker run --name redis-cache -p 6379:6379 --network redis-net -v C:\docker-redis\cache:/data/cache -d redis redis-server --appendonly yes

docker run --name redis-session -p 6380:6379 --network redis-net -v C:\docker-redis\session:/data/session -d redis redis-server --appendonly yes
```  
