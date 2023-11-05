FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# COPY --chown=appuser:appuser ms_cinema.jar app.jar
COPY cinema.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# USER appuser
ENTRYPOINT [ "/bin/sh", "-c", "/opt/java/openjdk/bin/java $JAVA_OPTS -jar /app.jar" ]
