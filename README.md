# Pixelarium

Pixelarium is a full-stack e-commerce platform for selling products (books, video games, technology, etc.). This repository contains both the **REST API backend** developed with **Spring Boot 4**, **MySQL**, and **Java 25**, and the **React frontend** for a complete user interface.

## Key Features

### Backend

- **Complete REST API**: Full CRUD operations for users, products, and orders
- **Search Functionality**: Query products by name and description in the database
- **CORS Enabled**: Configured for React development at `localhost:3000`
- **Response DTOs**: Does not expose sensitive data
- **Global Error Handling**: Consistent JSON responses across all endpoints
- **Validations**: Bean Validation on DTOs
- **Complete JavaDoc**: All backend methods documented
- **Business Logic**: State transitions, total calculations, order validation

### Frontend

- **React with Vite**: Modern development environment with fast builds
- **TypeScript**: Full type safety across the application
- **Context API**: Global state management for authentication and shopping cart
- **React Router**: Complete navigation with protected routes
- **Responsive Design**: Mobile-first dark UI theme
- **Product Search**: Real-time search from header navigating to products page
- **Shopping Cart**: Full cart management with persistence in localStorage
- **User Authentication**: Login and registration with session recovery
- **Product Filtering**: Category, price range, and sale filters with pagination
- **CSS Modules**: Scoped styling for each component

## Requirements

### Backend

- **Java**: 25 or higher
- **Maven**: 3.9 or higher
- **MySQL**: 8.0 or higher (or MariaDB 10.5+)
- **Git**: 2.40 or higher

### Frontend

- **npm**: 9 or higher
- **Node.js**: 22 or higher

## Installation and Setup

### Backend Setup

#### 1. Clone the repository

```bash
git clone https://github.com/NaXeMate/Pixelarium.git
cd Pixelarium
git checkout develop  # Or main for the latest stable version
```

#### 2. Configure the database

**Create the database**

```sql
CREATE DATABASE pixelarium CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'pixelarium_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON pixelarium.* TO 'pixelarium_user'@'localhost';
FLUSH PRIVILEGES;
```

**Configure `application.properties`**

Update `backend/src/main/resources/application.properties` to match your database configuration:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pixelarium?createDatabaseIfNotExist=true&allowMultiQueries=true
spring.datasource.username=pixelarium_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Data Initialization
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql

# Server
server.port=8080
```

#### 3. Build the project

```bash
cd backend
mvn clean install
```

#### 4. Run the application

```bash
mvn spring-boot:run
```

The API will be available at: http://localhost:8080.

### Frontend setup

#### 1. Navigate to the frontend directory

```bash
cd frontend/pixelarium
```

#### 2. Install dependencies

```bash
npm install
```

#### 3. Start the development server

```bash
npm run dev
```

The frontend will be available at: http://localhost:3000

**Important:** Make sure the backend is running at http://localhost:8080 before starting the frontend.

## Backend API Documentation

**Base URL:** http://localhost:8080/api

### Users (/api/users)

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
  "email": {
    "value": "mateo@example.com"
  },
  "registerTime": "2026-02-15"
}
```

### Products (/api/products)

| Method | Endpoint                          | Description                   | Query Params            |
| ------ | --------------------------------- | ----------------------------- | ----------------------- |
| GET    | /api/products                     | List all products             | -                       |
| GET    | /api/products/{id}                | Get product by ID             | -                       |
| GET    | /api/products/category/{category} | Filter by category            | -                       |
| GET    | /api/products/price-range         | Filter by price               | min, max                |
| GET    | /api/products/sale-offers         | Products on sale              | -                       |
| GET    | /api/products/search              | Search by name or description | query                   |
| POST   | /api/products                     | Create product                | CreateProductDTORequest |
| PUT    | /api/products/{id}                | Update product                | Product                 |
| DELETE | /api/products/{id}                | Delete product                | -                       |

**Example search by price range:**

```bash
curl "http://localhost:8080/api/products/price-range?min=10.00&max=50.00"
```

**Example search by text:**

```bash
curl "http://localhost:8080/api/products/search?query=Nintendo"
```

### Orders (/api/orders)

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

**Response (OrderDTOResponse):**

```json
{
  "id": 5,
  "userId": 1,
  "orderDate": "2026-02-15T16:45:00",
  "totalPrice": 129.97,
  "status": {
    "type": "DRAFT"
  },
  "orderItems": [
    {
      "id": 10,
      "productId": 1,
      "quantity": 2,
      "unitPrice": 49.99
    },
    {
      "id": 11,
      "productId": 3,
      "quantity": 1,
      "unitPrice": 29.99
    }
  ]
}
```

## Architecture

### Application Structure

```text
frontend/pixelarium/
├── src/
│   ├── assets/                # Images, fonts, and static files
│   ├── components/      # Reusable components
│   │   ├── layout/      # Header and Footer
│   │   ├── common/      # Generic components (ProtectedRoute, Button)
│   │   └── products/    # Product-specific components (ProductCard)
│   ├── context/         # Global state management
│   │   ├── AuthContext.tsx    # Authentication and user session
│   │   └── CartContext.tsx    # Shopping cart management
│   ├── pages/           # Page components
│   │   ├── home/        # Landing page with featured products
│   │   ├── products/    # Product listing with filters and search
│   │   ├── productDetail/     # Single product view
│   │   ├── cart/        # Shopping cart (protected)
│   │   ├── login/       # Login page (guest-only)
│   │   ├── register/    # Registration page (guest-only)
│   │   ├── notFoundPage/      # 404 page
│   │   └── errorPage/   # Error boundary page
│   ├── routes/          # React Router configuration
│   ├── services/        # API communication layer
│   │   ├── api.ts       # Base API configuration and request handler
│   │   ├── userService.ts     # User authentication and management
│   │   ├── productService.ts  # Product queries and search
│   │   └── orderService.ts    # Order management
│   ├── types/           # TypeScript type definitions
│   │   ├── cartTypes.ts       # Cart-related types
│   │   ├── productTypes.ts    # Product-related types
│   │   ├── userTypes.ts       # User-related types
│   │   ├── orderTypes.ts      # Order-related types
│   │   └── index.ts     # Type exports
│   ├── App.tsx          # Main application component
│   ├── App.css          # Global application styles
│   └── main.tsx         # Vite entry point
├── index.html
├── vite.config.ts
├── tsconfig.json
├── package.json
└── README.md
```

### Pages Overview

#### Home (/):

- Hero section with call-to-action buttons

- Quick-access category cards

- Featured offers carousel (8 products from sale-offers endpoint)

- Newsletter subscription section

#### Products (/products):

- Full product catalogue with server-side data

- Sidebar filters: category (radio-style), price range (min/max), sale-only toggle

- Server-side search: via ?search=query URL parameter

- Client-side sorting: price ascending/descending, name alphabetical

- Grid display with pagination (8 items per page)

- Empty state with filter clearing

#### Product Detail (/products/:id):

- Product image, name, category, and full description

- Current price with sale price if available

- Discount percentage badge

- Authenticated users: Add to cart button with quantity selector

- Unauthenticated users: Login prompt

- Related products section

#### Cart (/cart) - Protected Route:

- List of all cart items with images and prices

- Quantity controls (increment, decrement, remove)

- Subtotal and total price calculation

- Place order button (creates order and redirects to home)

- Empty cart state with link to continue shopping

#### Login (/login) - Guest Only:

- Email and password fields

- Client-side validation

- Automatic redirect to home if already logged in

- Link to registration page

- Remember me checkbox

#### Register (/register) - Guest Only:

- Form fields: username, email, first name, last name, date of birth, password

- Age validation: Must be 18 or older

- Password strength indicator (weak, medium, strong) with visual feedback

- Show/hide password toggle

- Field-level validation with inline error messages

- Automatic login on successful registration

- Link to login page

#### State Management

**AuthContext**

- User State: Current authenticated user or null

- Loading State: Tracks session recovery from localStorage

- Methods: login(), register(), logout()

- Persistence: User session saved in localStorage, restored on app mount

**CartContext**

- Items State: Array of cart items (product + quantity)

- Methods: addToCart(), removeFromCart(), updateQuantity(), clearCart()

- Calculations: getTotalPrice(), getTotalItems()

- Persistence: Cart saved to localStorage and synced on every change

#### Routing and Access Control

- Public Routes: Home, Products, Product Detail

- Guest-Only Routes: Login, Register (redirect to home if authenticated)

- Protected Routes: Cart (redirect to login if not authenticated)

- Loading Guard: ProtectedRoute prevents premature navigation during session recovery

#### API Integration

All API calls go through `src/services/api.ts` which provides:

- Centralized configuration with API_BASE_URL

- Generic apiRequest<T>() function with TypeScript generics

- Automatic error handling with meaningful messages

- Request and response transformation (JSON stringify/parse)

Each service file (userService, productService, orderService) exports functions that:

- Call the appropriate backend endpoint

- Accept typed parameters

- Return typed promises

- Throw errors for failed requests

#### Frontend Configuration (CORS)

CORS is configured on the backend to accept requests from:

- Development: http://localhost:3000 (Vite development server)

- Allowed Methods: GET, POST, PUT, DELETE

- Allowed Headers: Content-Type, Authorization

### Backend Project Structure

```text
backend/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── edu/
    │   │           └── mqt/
    │   │               └── pixelarium/
    │   │                   ├── PixelariumApplication.java
    │   │                   ├── config/          # Security and app configuration
    │   │                   ├── controller/      # REST Controllers (web layer)
    │   │                   ├── exception/       # Global exception handling
    │   │                   ├── model/           # JPA Entities and DTOs
    │   │                   ├── repository/      # Spring Data JPA Repositories
    │   │                   └── service/         # Business logic services
    │   └── resources/
    │       ├── application.properties   # App configuration
    │       └── data.sql                 # Initial data seeding
    └── test/                            # Unit and integration tests
```
