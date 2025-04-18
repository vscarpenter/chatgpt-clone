# ChatGPT Clone with AWS Bedrock

A Spring Boot web application that mimics the ChatGPT interface, powered by AWS Bedrock's Claude model.

## Features

- Clean, modern UI similar to ChatGPT
- Integration with AWS Bedrock's Claude model
- Real-time chat interface
- Proper formatting of responses with code blocks and line breaks
- Docker support for easy deployment

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- AWS account with access to Bedrock service
- AWS credentials configured (either through environment variables or AWS credentials file)
- Docker (optional, for containerized deployment)

## Configuration

The application requires the following AWS configuration:

1. AWS credentials must be configured either through:
   - Environment variables:
     ```bash
     export AWS_ACCESS_KEY_ID=your_access_key
     export AWS_SECRET_ACCESS_KEY=your_secret_key
     export AWS_REGION=us-east-1
     ```
   - Or AWS credentials file (~/.aws/credentials):
     ```
     [default]
     aws_access_key_id = your_access_key
     aws_secret_access_key = your_secret_key
     region = us-east-1
     ```

2. Application properties can be configured in `src/main/resources/application.yml`:
   ```yaml
   aws:
     bedrock:
       region: us-east-1
       model-id: anthropic.claude-v2:1
       temperature: 0.7
       max-tokens: 1000
   ```

## Building and Running

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/chatgpt-clone.git
   cd chatgpt-clone
   ```

2. Build the application:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   java -jar target/chatgpt-clone-0.0.1-SNAPSHOT.jar
   ```

4. Access the application at: `http://localhost:8080`

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

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request 