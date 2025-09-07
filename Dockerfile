# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Install necessary packages
RUN apt-get update && apt-get install -y \
    x11-apps \
    xauth \
    xvfb \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Copy MySQL connector if not already in lib folder
RUN mkdir -p lib && \
    if [ ! -f lib/mysql-connector-java-8.0.28.jar ]; then \
        wget -O lib/mysql-connector-java-8.0.28.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar; \
    fi

# Create build directory
RUN mkdir -p build/classes

# Compile Java classes
RUN javac -cp "lib/*" -d build/classes src/electricity/billing/system/*.java

# Set display for X11 forwarding
ENV DISPLAY=:0

# Expose port for any potential web interface
EXPOSE 8080

# Run the application
CMD ["java", "-cp", "lib/*:build/classes", "electricity.billing.system.Splash"]
