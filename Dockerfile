FROM openjdk:11
ADD target/*.jar deploy/user-management.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar", "deploy/user-management.jar"]