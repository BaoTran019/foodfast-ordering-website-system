FROM eclipse-temurin:21-jdk

# Tạo group spring và user spring
RUN groupadd spring && useradd -m -g spring spring

# Dùng user spring để khởi động ứng dụng Spring Boot sẽ an toàn hơn
USER spring:spring

# Đặt biến JAR_FILE là file có đuôi jar trong thư mục target
ARG JAR_FILE=target/*.jar

# Copy JAR_FILE vào docker image với tên file mới là app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]