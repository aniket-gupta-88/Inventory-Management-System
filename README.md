# 🏪 Inventory Management System

A complete **Spring Boot 3** based Inventory Management System to manage **products, suppliers, users, and transactions** (purchase, sale, return).  
It includes **JWT-based authentication**, **role-based access control** (Admin/User), and CRUD APIs for all major entities.

## 🚀 Tech Stack

- **Backend:** Spring Boot 3, Spring Security, Spring Data JPA, Hibernate
- **Database:** MySQL / PostgreSQL
- **Validation:** Jakarta Validation
- **Authentication:** JWT-based
- **Build Tool:** Maven
- **Language:** Java 17+
- **Image Upload:** MultipartFile support in Product APIs

## 📂 Project Structure

com.myproject.InventoryManagementSystem
├── entity
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Supplier.java
│   └── Transaction.java
│
├── controller
│   ├── AuthController.java
│   ├── UserController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── SupplierController.java
│   └── TransactionController.java
│
├── service
│   ├── UserService.java
│   ├── ProductService.java
│   ├── CategoryService.java
│   ├── SupplierService.java
│   └── TransactionService.java
│
├── repository
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   ├── SupplierRepository.java
│   └── TransactionRepository.java
│
└── InventoryManagementSystemApplication.java


## 🧩 Entity Relationships

| Entity      | Relationship | Description                               |
| ----------- | ------------ | ----------------------------------------- |
| User        | One-to-Many  | A user can make many transactions         |
| Transaction | Many-to-One  | Linked to one product, supplier, and user |
| Product     | Many-to-One  | Belongs to one category                   |
| Category    | One-to-Many  | Contains multiple products                |
| Supplier    | One-to-Many  | Can supply multiple transactions          |

## 🔐 Authentication APIs (`/api/auth`)

| Method | Endpoint    | Description                  |
| ------ | ----------- | ---------------------------- |
| POST   | `/register` | Register new user            |
| POST   | `/login`    | Login user and get JWT token |

## 👥 User APIs (`/api/users`)

| Method | Endpoint                 | Access     | Description                  |
| ------ | ------------------------ | ---------- | ---------------------------- |
| GET    | `/all`                   | ADMIN      | Get all users                |
| PUT    | `/update/{id}`           | USER/ADMIN | Update user info             |
| DELETE | `/delete/{id}`           | ADMIN      | Delete a user                |
| GET    | `/transactions/{userid}` | USER/ADMIN | Fetch user transactions      |
| GET    | `/current`               | USER       | Get currently logged-in user |

## 📦 Product APIs (`/api/products`)

| Method | Endpoint       | Access | Description            |
| ------ | -------------- | ------ | ---------------------- |
| POST   | `/add`         | ADMIN  | Add product with image |
| PUT    | `/update`      | ADMIN  | Update product info    |
| GET    | `/all`         | PUBLIC | Get all products       |
| GET    | `/{id}`        | PUBLIC | Get product by ID      |
| DELETE | `/delete/{id}` | ADMIN  | Delete product         |

## 🗂 Category APIs (`/api/categories`)

| Method | Endpoint       | Access | Description        |
| ------ | -------------- | ------ | ------------------ |
| POST   | `/add`         | ADMIN  | Add category       |
| GET    | `/all`         | PUBLIC | Get all categories |
| GET    | `/{id}`        | PUBLIC | Get category by ID |
| PUT    | `/update/{id}` | ADMIN  | Update category    |
| DELETE | `/delete/{id}` | ADMIN  | Delete category    |

## 🚚 Supplier APIs (`/api/suppliers`)

| Method | Endpoint       | Access | Description        |
| ------ | -------------- | ------ | ------------------ |
| POST   | `/add`         | ADMIN  | Add supplier       |
| GET    | `/all`         | PUBLIC | Get all suppliers  |
| GET    | `/{id}`        | PUBLIC | Get supplier by ID |
| PUT    | `/update/{id}` | ADMIN  | Update supplier    |
| DELETE | `/delete/{id}` | ADMIN  | Delete supplier    |

## 💳 Transaction APIs (`/api/transactions`)

| Method | Endpoint         | Description                        |
| ------ | ---------------- | ---------------------------------- |
| POST   | `/purchase`      | Restock inventory (purchase)       |
| POST   | `/sell`          | Sell product to customer           |
| POST   | `/return`        | Return product to supplier         |
| GET    | `/all`           | Get all transactions (paginated)   |
| GET    | `/{id}`          | Get transaction by ID              |
| GET    | `/by-month-year` | Get transactions by month and year |
| PUT    | `/update/{id}`   | Update transaction status          |

## ⚙️ Setup & Run

### 1️⃣ Clone Repository

```bash
git clone https://github.com/<your-username>/inventory-management-system.git
cd inventory-management-system
```

### 2️⃣ Configure Database

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

### 3️⃣ Build & Run

```bash
mvn clean install
mvn spring-boot:run
```
