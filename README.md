# EasyShop API v2 🛒

A robust and feature-rich e-commerce REST API built with Spring Boot, providing comprehensive functionality for managing an online shopping platform.

## 🚀 Features

### Core Functionality

- **User Authentication & Authorization** - JWT-based security with role-based access control
- **Product Management** - Complete CRUD operations for products with categories
- **Shopping Cart** - Full shopping cart functionality with persistent storage
- **User Profiles** - Comprehensive user profile management
- **Category Management** - Organize products with hierarchical categories
- **Order Processing** - Complete order management system

### Security Features

- JWT token-based authentication
- Role-based authorization (ADMIN/USER)
- Secure password hashing with BCrypt
- Protected endpoints with Spring Security

### Database Features

- MySQL database with optimized schema
- Connection pooling with Apache DBCP2
- MyBatis for efficient data access
- Comprehensive database seeding with sample data

## 🛠️ Technology Stack

- **Framework**: Spring Boot 2.7.14
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT
- **Data Access**: MyBatis
- **Build Tool**: Maven
- **Testing**: JUnit + Spring Boot Test

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/easyshop-api-v2.git
cd easyshop-api-v2
```

### 2. Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Create and populate the database
source database/create_database.sql
```

### 3. Configure Application Properties

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easyshop
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📚 API Endpoints

### Authentication

| Method | Endpoint         | Description       | Access |
| ------ | ---------------- | ----------------- | ------ |
| POST   | `/auth/login`    | User login        | Public |
| POST   | `/auth/register` | User registration | Public |

### Categories

| Method | Endpoint           | Description         | Access |
| ------ | ------------------ | ------------------- | ------ |
| GET    | `/categories`      | Get all categories  | Public |
| GET    | `/categories/{id}` | Get category by ID  | Public |
| POST   | `/categories`      | Create new category | Admin  |
| PUT    | `/categories/{id}` | Update category     | Admin  |
| DELETE | `/categories/{id}` | Delete category     | Admin  |

### Products

| Method | Endpoint         | Description                     | Access |
| ------ | ---------------- | ------------------------------- | ------ |
| GET    | `/products`      | Get all products (with filters) | Public |
| GET    | `/products/{id}` | Get product by ID               | Public |
| POST   | `/products`      | Create new product              | Admin  |
| PUT    | `/products/{id}` | Update product                  | Admin  |
| DELETE | `/products/{id}` | Delete product                  | Admin  |

### Shopping Cart

| Method | Endpoint                     | Description      | Access |
| ------ | ---------------------------- | ---------------- | ------ |
| GET    | `/cart`                      | Get user's cart  | User   |
| POST   | `/cart/products/{productId}` | Add item to cart | User   |
| PUT    | `/cart/products/{productId}` | Update cart item | User   |
| DELETE | `/cart`                      | Clear cart       | User   |

## 📊 Database Schema

The application uses a well-structured MySQL database with the following main entities:

- **users** - User accounts and authentication
- **profiles** - User profile information
- **categories** - Product categories
- **products** - Product catalog
- **shopping_cart** - Shopping cart items
- **orders** - Order history
- **order_line_items** - Order details

## 🔐 Authentication

The API uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. Login with valid credentials to receive a JWT token
2. Include the token in the Authorization header: `Bearer <your-token>`
3. Tokens are valid for 30 hours by default

### Default Test Users

- **Admin**: username: `admin`, password: `password`
- **User**: username: `user`, password: `password`

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

The project includes comprehensive unit tests for:

- Data access layer (DAO)
- Service layer logic
- Controller endpoints
- Security configurations

## 📁 Project Structure

```
src/
├── main/
│   ├── java/org/yearup/
│   │   ├── configurations/     # Database and app configuration
│   │   ├── controllers/        # REST API controllers
│   │   ├── data/              # Data access layer (DAOs)
│   │   ├── models/            # Entity models and DTOs
│   │   ├── security/          # Security configuration and JWT
│   │   └── EasyshopApplication.java
│   └── resources/
│       ├── application.properties
│       └── banner.txt
└── test/                      # Test classes
```

## 🚀 Deployment

### Local Development

```bash
mvn spring-boot:run
```

### Production Build

```bash
mvn clean package
java -jar target/easyshop-api-v2-1.0-SNAPSHOT.jar
```

### Docker (Optional)

```dockerfile
FROM openjdk:17-jre-slim
COPY target/easyshop-api-v2-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔧 Configuration

Key configuration properties in `application.properties`:

- **Database**: Connection URL, username, and password
- **JWT**: Secret key and token expiration time
- **Server**: Port configuration (default: 8080)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 API Documentation

For detailed API documentation including request/response examples, consider integrating Swagger/OpenAPI:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.7.0</version>
</dependency>
```

## 🐛 Known Issues

- None currently reported

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support, please email support@easyshop.com or create an issue in this repository.

## 🔄 Version History

- **v2.0** - Current version with enhanced security and performance
- **v1.0** - Initial release

---

Made with ❤️ by [YearUp Development Team]
