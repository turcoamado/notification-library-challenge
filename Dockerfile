FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
# Execute examples class.
CMD ["java","-cp","target/notifications-lib-1.0.0.jar","com.pinapp.notifications.examples.NotificationExamples"]
