FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

COPY ../applications/app-service/build/libs/*.jar NameProject.jar
# ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
# USER appuser

EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar NameProject.jar" ]

