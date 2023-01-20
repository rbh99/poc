
# docker file 
FROM openjdk:17-alpine

# This time we configure a non-root user in the Dockerfile to build
# a container image that will run using without the root user.
#RUN addgroup --system --gid 11000 appgroup && adduser --system --uid 11000 --gid 11000  springuser
#USER appgroup:springuser



# RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
#WORKDIR /app
# RUN chown -R javauser:javauser /app
# USER javauser



#RUN addgroup --system --gid 1002 app && adduser --system --uid 1002 --gid 1002 appuser
#USER 1002
#docker container run --rm --detach --name <container-name> -p 9090:9090 andifalk/library-server-container-rootless:latest
#docker exec <container-name> whoami  should return user

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} echo-app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/echo-app.jar"]

# use a lightweight init system, to properly initialize java processes with signal support (to be able to kill pid), basically avoiding PID 1
#CMD "dumb-init" "java" "-jar" "/echo-app.jar"