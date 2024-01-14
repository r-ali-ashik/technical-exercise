# Use Ubuntu as the base image
FROM raliashik/ubuntu-jdk8:1.0

COPY target/technical-exercise.jar /app/target/

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "target/technical-exercise.jar"]
