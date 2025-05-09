# ChatGPT Clone with AWS Bedrock

A Spring Boot web application that mimics the ChatGPT interface, powered by AWS Bedrock's Claude model.

## Features

- Clean, modern UI similar to ChatGPT
- Integration with AWS Bedrock's Claude model
- Real-time chat interface
- Proper formatting of responses with code blocks and line breaks
- Docker support for easy deployment
- Health monitoring and graceful shutdown via Spring Actuator

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- AWS account with access to Bedrock service
- AWS credentials configured (either through environment variables or AWS credentials file)
- Docker (optional, for containerized deployment)

## Configuration

The application requires the following environment variables:

```bash
# AWS Credentials
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=us-east-1

# AWS Bedrock Configuration
export AWS_BEDROCK_MODEL_ID=anthropic.claude-v2:1
export AWS_BEDROCK_TEMPERATURE=0.7
export AWS_BEDROCK_MAX_TOKENS=1000
```

Alternatively, you can configure AWS credentials through the AWS credentials file (~/.aws/credentials):
```
[default]
aws_access_key_id = your_access_key
aws_secret_access_key = your_secret_key
region = us-east-1
```

## Building and Running

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/chatgpt-clone.git
   cd chatgpt-clone
   ```

2. Set up environment variables (as shown in Configuration section)

3. Build the application:
   ```bash
   mvn clean package
   ```

4. Run the application:
   ```bash
   java -jar target/chatgpt-clone-0.0.1-SNAPSHOT.jar
   ```

5. Access the application at: `http://localhost:8080`

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t chatgpt-clone .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 \
     -e AWS_ACCESS_KEY_ID=your_access_key \
     -e AWS_SECRET_ACCESS_KEY=your_secret_key \
     -e AWS_REGION=us-east-1 \
     -e AWS_BEDROCK_MODEL_ID=anthropic.claude-v2:1 \
     -e AWS_BEDROCK_TEMPERATURE=0.7 \
     -e AWS_BEDROCK_MAX_TOKENS=1000 \
     chatgpt-clone
   ```

3. Access the application at: `http://localhost:8080`

## Project Structure

```
chatgpt-clone/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/chatgptclone/
│   │   │       ├── config/
│   │   │       │   └── AwsBedrockProperties.java
│   │   │       ├── controller/
│   │   │       │   └── ChatController.java
│   │   │       ├── service/
│   │   │       │   └── BedrockService.java
│   │   │       └── ChatGptCloneApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       │   └── index.html
│   │       └── application.yml
│   └── test/
├── Dockerfile
├── pom.xml
└── README.md
```

## Technologies Used

- Spring Boot 3.2.3
- AWS SDK for Java 2.x
- Thymeleaf
- Lombok
- Docker

## Security

- No hardcoded credentials in the codebase
- AWS credentials handled through environment variables or AWS credentials file
- Input validation and sanitization
- Security headers for web protection
- Proper error handling without exposing internal details

## Monitoring

The application includes Spring Actuator endpoints for monitoring and management:

- Health Check: `GET /actuator/health`
- Graceful Shutdown: `POST /actuator/shutdown` (requires authentication)

These endpoints provide system health information and allow for proper application shutdown.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request 