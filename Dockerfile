FROM openjdk:11
EXPOSE 7000
ADD target/tweetApplication.jar tweetApplication.jar
ENTRYPOINT ["java", "-jar", "/tweetApplication.jar"]