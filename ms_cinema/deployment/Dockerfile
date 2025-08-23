FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# Replace with a non-root user to avoid running the container with excessive privileges
# COPY --chown=appuser:appuser ms_cinema.jar ms_cinema.jar
COPY ms_cinema.jar ms_cinema.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
# USER appuser
ENTRYPOINT [ "/bin/sh", "-c", "/opt/java/openjdk/bin/java $JAVA_OPTS -jar /ms_cinema.jar" ]