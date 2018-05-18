FROM maven:3.5-jdk-8
WORKDIR /app/eureka-server
RUN ls -ls && pwd
COPY .
COPY pom.xml /app
## 构建应用
RUN mvn clean install -DskipTests
## 拷贝编译结果到指定目录
RUN mv /app/spring-cloud-eureka/spring-cloud-eureka-server/target/*.jar /app/app-tx.jar \
    ## 清理编译痕迹
    && cd /app && rm -rf /app/eureka-server

VOLUME /app
## 暴露容器内端口
EXPOSE 8080
## 启动服务
ENTRYPOINT ["java","-jar","/app/app-tx.jar"]
CMD ["--SERVER_PORT=8080"]