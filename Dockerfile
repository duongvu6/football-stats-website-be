FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Create directories for file storage
RUN mkdir -p /app/images/club
RUN mkdir -p /app/images/coach
RUN mkdir -p /app/images/league
RUN mkdir -p /app/images/player

# Add a non-root user to run the application
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the jar file
COPY epl-web/target/*.jar app.jar

# Set ownership to the non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Set JVM options (adjust as needed)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV UPLOAD_FILE_BASE_URI="file:///app/images/"

# Run the application
ENTRYPOINT exec java $JAVA_OPTS -Dupload-file.base-uri=${UPLOAD_FILE_BASE_URI} -jar app.jar