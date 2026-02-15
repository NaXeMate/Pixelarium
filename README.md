# Pixelarium

Pixelarium is an e-commerce platform for selling products (books, video games, etc.). This repository contains the **REST API** developed with **Spring Boot 4**, **MySQL**, and **Java 25**, ready to integrate with a **React** frontend.

## ✨ Key Features

- ✅ **Complete REST API**: CRRUD for users, products, and orders
- ✅ **CORS Enabled**: Configured for React at `localhost:3000`
- ✅ **Response DTOs**: Does not expose sensitive data
- ✅ **Global Error Handling**: Consistent JSON responses
- ✅ **Validations**: Bean Validation on DTOs
- ✅ **Complete JavaDoc**: All methods documented
- ✅ **Business Logic**: State transitions, total calculations, validations

## 📋 Requirements

- **Java**: 25 or higher
- **Maven**: 3.8.1 or higher
- **MySQL**: 8.0 or higher (or MariaDB 10.5+)
- **Git**: 2.52 or higher

## 🛠️ Installation and Setup

### 1. Clone the repository

```bash
git clone https://github.com/NaXeMate/Pixelarium.git
cd Pixelarium
git checkout main  # Or develop for the latest version
```

### 2. Configure the database

#### Create the database

```sql
CREATE DATABASE pixelarium CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'pixelarium_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON pixelarium.* TO 'pixelarium_user'@'localhost';
FLUSH PRIVILEGES;
```

#### Configure application.properties

Edit `src/main/resources/application.properties`:

```java
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pixelarium
spring.datasource.username=pixelarium_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update  # create-drop for development
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server
server.port=8080

# CORS (already configured in code)
# Allows connections from React at localhost:3000
```

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The API will be available at: http://localhost:8080

### API Documentation

**Base URL**: `http://localhost:8080`

#### Users (/api/users)

| Method | Endpoint                 | Description        | Body                 |
| ------ | ------------------------ | ------------------ | -------------------- |
| GET    | /api/users               | List all users     | -                    |
| GET    | /api/users/{id}          | Get user by ID     | -                    |
| GET    | /api/users/email/{email} | Find user by email | -                    |
| POST   | /api/users               | Create user        | CreateUserDTORequest |
| PUT    | /api/users/{id}          | Update user        | User                 |
| DELETE | /api/users/{id}          | Delete user        | -                    |

**Example check user creation:**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "mateo@example.com",
    "password": "password123",
    "firstName": "Mateo",
    "lastName": "García",
    "userName": "mateo.garcia"
  }'
```

**Response (UserDTOResponse):**

```json
{
  "id": 1,
  "userName": "mateo.garcia",
  "realName": "Mateo",
  "surname": "García",
  "email": "mateo@example.com",
  "registerTime": "2026-02-15"
}
```

#### Products (/api/products)

| Method | Endpoint                          | Description        | Query Params            |
| ------ | --------------------------------- | ------------------ | ----------------------- |
| GET    | /api/products                     | List all products  | -                       |
| GET    | /api/products/{id}                | Get product by ID  | -                       |
| GET    | /api/products/category/{category} | Filter by category | -                       |
| GET    | /api/products/price-range         | Filter by price    | min, max                |
| GET    | /api/products/sale-offers         | Products on sale   | -                       |
| POST   | /api/products                     | Create product     | CreateProductDTORequest |
| PUT    | /api/products/{id}                | Update product     | Product                 |
| DELETE | /api/products/{id}                | Delete product     | -                       |

**Example search by price range:**

```bash
curl "http://localhost:8080/api/products/price-range?min=10.00&max=50.00"
```

#### Orders (/api/orders)

| Method | Endpoint                             | Description      | Body/Params                     |
| ------ | ------------------------------------ | ---------------- | ------------------------------- |
| GET    | /api/orders                          | List all orders  | -                               |
| GET    | /api/orders/{id}                     | Get order by ID  | -                               |
| GET    | /api/orders/user/{userId}            | Orders by user   | -                               |
| GET    | /api/orders/status/{statusType}      | Filter by status | DRAFT, PENDING, SENT, DELIVERED |
| GET    | /api/orders/date/{date}              | Filter by date   | ISO 8601                        |
| POST   | /api/orders                          | Create order     | CreateOrderDTORequest           |
| PUT    | /api/orders/{id}                     | Update order     | Order                           |
| PUT    | /api/orders/{id}/status/{statusType} | Change status    | -                               |
| POST   | /api/orders/{id}/cancel              | Cancel order     | -                               |
| DELETE | /api/orders/{id}                     | Delete order     | -                               |

**Example order creation:**

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "items": [
      {"productId": 1, "quantity": 2},
      {"productId": 3, "quantity": 1}
    ]
  }'
```

**Response:**

```json
{
  "id": 5,
  "user": {...},
  "status": {"type": "DRAFT"},
  "orderDate": "2026-02-15T16:45:00",
  "totalPrice": 129.97,
  "orderItems": [...]
}
```

### Frontend Configuration (React)

**CORS is already configured**

The backend allows requests from:

- **Development**: http://localhost:3000
- **Allowed Methods**: GET, POST, PUT, DELETE
- **Allowed Headers**: Content-Type, Authorization

#### Connect from React

```javascript
// src/services/api.js
const API_BASE_URL = "http://localhost:8080/api";

export const getProducts = async () => {
  const response = await fetch(`${API_BASE_URL}/products`);
  if (!response.ok) throw new Error("Failed to fetch products");
  return response.json();
};

export const createUser = async (userData) => {
  const response = await fetch(`${API_BASE_URL}/users`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!response.ok) throw new Error("Failed to create user");
  return response.json();
};
```

### Project Structure

```text
src/main/java/com/edu/mqt/pixelarium/
├── config/                 # Configurations (CORS, security)
│   └── CorsConfig.java
├── exception/              # Global exception handling
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── ErrorResponse.java
├── model/                  # JPA Entities
│   ├── User.java
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── dto/
│   │   ├── request/        # Request DTOs
│   │   │   ├── CreateUserDTORequest.java
│   │   │   ├── CreateProductDTORequest.java
│   │   │   └── CreateOrderDTORequest.java
│   │   └── response/       # Response DTOs
│   │       ├── UserDTOResponse.java
│   │       ├── ProductDTOResponse.java
│   │       └── OrderDTOResponse.java
│   ├── vo/                 # Value Objects
│   │   ├── Email.java
│   │   └── Status.java
│   └── enumerated/         # Enums
│       └── Category.java
├── repositories/           # JPA Repositories
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   └── OrderRepository.java
├── service/                # Business Logic
│   ├── UserService.java
│   ├── ProductService.java
│   └── OrderService.java
├── web/                    # REST Controllers
│   ├── UserController.java
│   ├── ProductController.java
│   └── OrderController.java
└── PixelariumApplication.java
```

### Testing

#### Using curl

```bash
# List users
curl http://localhost:8080/api/users

# Create product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "The Legend of Zelda",
    "description": "Epic adventure",
    "price": 59.99,
    "salePrice": 49.99,
    "imagePath": "/images/zelda.jpg",
    "stock": 50,
    "category": "VIDEOGAMES"
  }'
```

#### Using Postman

Import the endpoint collection available at /docs/Pixelarium.postman_collection.json (coming soon).

### Error Handling

The API returns errors in a consistent JSON format:

#### Example 404 Error:

```json
{
  "status": 404,
  "message": "Resource not found",
  "detail": "User with id 999 not found"
}
```

#### Example Validation Error (400):

```json
{
  "status": 400,
  "message": "Invalid request",
  "detail": "Email is required"
}
```
