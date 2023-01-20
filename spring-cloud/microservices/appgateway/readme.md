# Example of integration for microservices

# servers

- discovery server: eureka discovery server
- configserver: configuration server
- appgateway: the main entry point to the application. it exposes a rest get uri   /app/{name}
- aservice: appgateway will call this service to process the input
- bservice: a service is calling b service for further processing, before ansembling back the request to appgateway


## discovery server
simple eureka server configuration


## configserver

## appgateway

uses feign clients to connect to aservice

## aservice

## bservice


# interesting links
[https://dzone.com/articles/quick-guide-to-microservices-with-spring-boot-20-e](https://dzone.com/articles/quick-guide-to-microservices-with-spring-boot-20-e)


https://dzone.com/articles/quick-guide-to-microservices-with-spring-boot-20-e