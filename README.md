# Simple blog application
This is simple blog built with spring boot project

# Requirement
This project need [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed in your environment

MySQL is running on port 3306

MAVEN install on your system


# Running this application locally

open file ./src/main/resources/application.properties

replace `<<USERNAME>>` with your DB username

replace `<<PASSWORD>>` with your DB password

replace `<<HOST>>` with your email host server

replace `<<PORT>>` with your email port

replace `<<MAIL_USERNAME>>` with email username

replace `<<MAIL_PASSWORD>>` with email pasword


Next Go to project root directory and run command
```shell
mvn spring-boot:run
```

Open API document [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Enjoy !**
