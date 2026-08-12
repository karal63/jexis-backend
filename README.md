# Jexis Backend

Jexis Backend is a robust and scalable Spring Boot application that serves as the core API for the Jexis platform. It manages authentication, user profiles, financial transactions via Stripe, and asynchronous email notifications.

## 🚀 Features

- **Authentication & Authorization**: Secure JWT-based authentication with access and refresh tokens.
- **Financial Integration**: Full integration with Stripe for payments, wallets, and card management.
- **Asynchronous Processing**: Uses RabbitMQ for background tasks like sending emails.
- **Email Service**: Automated transactional emails (activation links, password resets).
- **Modular Architecture**: Clean code structure organized by domain logic.
- **API Documentation**: Interactive documentation powered by Swagger/OpenAPI.

## 🛠 Tech Stack

- **Framework**: Spring Boot 4.0.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **Messaging**: RabbitMQ
- **Security**: Spring Security, JWT (JJWT)
- **Integrations**: Stripe API
- **Documentation**: Springdoc-OpenAPI
- **Build Tool**: Gradle

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
- **Java 17** or higher
- **Docker** and **Docker Compose**
- **PostgreSQL** (if not running via Docker)
- **Postman** (for API testing)

## ⚙️ Setup & Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/jexis-backend.git
   cd jexis-backend
   ```

2. **Configure Environment:**
   Edit `src/main/resources/application.properties` to set your local environment variables:
   - Database credentials
   - JWT secrets
   - Stripe API keys and webhook secrets
   - Mail server credentials

3. **Start Infrastructure:**
   Use Docker Compose to start RabbitMQ:
   ```bash
   docker-compose up -d
   ```

4. **Database Setup:**
   Ensure a PostgreSQL database named `jexis` exists locally or update the connection string in `application.properties`.

5. **Run the Application:**
   ```bash
   ./gradlew bootRun
   ```
   The server will start on `http://localhost:3000/api`.

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger UI at:
[http://localhost:3000/api/swagger-ui/index.html](http://localhost:3000/api/swagger-ui/index.html)

A Postman collection is also available for testing:
[Postman Collection](postman-collection.json)

## 🏗 Project Structure

The project follows a modular structure:
- `auth`: Authentication and login logic.
- `user`: User management and profile handling.
- `wallet`: Financial wallet operations.
- `card`: Card issuance and management.
- `emailService`: Asynchronous email delivery system.

## 🤝 Contributing

Contributions are welcome! Please follow these steps:
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
