# EvaluationJava - Training Sales App

A console-based Java application for managing and selling training courses, developed as part of a business evaluation project

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)
- [Features Details](#features-details)
- [UML Diagrams](#uml-diagrams)
- [Code Quality](#code-quality)
- [Troubleshooting](#troubleshooting)

## Overview

This application allows users to browse training courses, manage a shopping cart, and place orders. It implements a multi-layered architecture (Entity, DAO, Business, Application)

## Features

### For Visitors (Unauthenticated Users)
- Browse all available courses
- Search courses by keyword
- Filter courses by type (In-person/Remote)
- Create an account
- Login

### For Authenticated Users
- All visitor features
- Manage shopping cart (add/remove courses)
- Place orders with client information
- View order history
- Logout

### For Admin
- View all courses
- Add a new course
- Update an existing course
- Delete an existing course
- View course details
- Logout

### Course Characteristics
- Name
- Description
- Duration (in days)
- Type (In-person or Remote)
- Price

## Architecture
The application follows a **multi-layered architecture**:
- Application Layer (Console UI)
- Business Logic Layer
- Data Access (Dao)
- Configuration & Utilities
- Database (MariaDB)

## Technologies

- **Language**: Java 8+
- **Database**: MariaDB 10.x
- **JDBC**: MariaDB Connector/J
- **Code Quality**: SonarQube
- **Version Control**: Git

## Prerequisites

- Java JDK 8 or higher
- MariaDB 10.x or MySQL 8.x
- MariaDB JDBC Driver (`mariadb-java-client.jar`)
- Git (for version control)

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/john7440/EvaluationJava.git
cd EvaluationJava
```
### 2. Open in IntelliJ IDEA

1. File → Open → Select the project folder
2. Wait for IntelliJ to index the project
3. Right-click on `lib/` folder → Add as Library
4. Click OK when prompted

Note : The MariaDB JDBC driver (mariadb-java-client.jar) is already included in the lib/ folder

## Database Setup

### Option 1 : Using MySQL Client
1. Open your MySQL/MariaDB client
2. Copy and paste the content of `doc/SQL/ScriptSQL.txt`
3. Execute the script

### Option 2: Comman line
```bash
mysql -u root -p < doc/SQL/ScriptSQL.txt
```
Note: The script will create the database, user, and populate data automatically

## Configuration

The database configuration is located in `resources/database.properties`:
```bash
db.url=jdbc:mariadb://localhost:3306/training_sales_db
db.user=training_user
db.password=Training@2026
db.driver=org.mariadb.jdbc.Driver
```
Note: Update credentials if you used different values during database setup !

## Usage

### Run the application

Option A: From IntelliJ IDEA (Recommended)
1. Navigate to `src/application/MainApp.java`
2. Right-click on the file
3. Select Run 'MainApp.main()'

Option B: From Command Line
Compile (if needed):
```bash
javac -d bin -cp ".:lib/*" src/**/*.java
```
Then run (for Windows users):
```bash
java -cp "bin;resources;lib/*" application.MainApp
```
#### Test accounts
For testing purposes, you already have 3 accounts:
- admin : admin123 (ADMIN)
- testuser : password (USER)
- john.doe : test2026 (USER)

#### Navigation
1. **Main Menu**: Choose between visitor and user options
2. **Browse Courses**: View all courses or search/filter
3. **Cart Management**: Add/remove courses (requires login)
4. **Place Order**: Provide client information and confirm
5. **Order History**: View past orders (requires login)

## Project Structure
```text
training-sales-app/
├── src/
│   ├── application/
│   │   ├── MainApp.java              # Entry point
│   │   ├── AdminMenu.java            # Admin menu display and logic
│   │   ├── InputHelper.java          # Helper class for input validation
│   │   ├── MenuDisplay.java          # Menu display utility
│   │   └── MenuHandler.java          # Menu logic handler
│   ├── business/
│   │   ├── UserBusiness.java         # User business logic
│   │   ├── CourseBusiness.java       # Course business logic
│   │   ├── CartBusiness.java         # Cart business logic
│   │   └── OrderBusiness.java        # Order business logic
│   ├── dao/
│   │   ├── IDao.java                 # Generic DAO interface
│   │   ├── UserDao.java              # User data access
│   │   ├── ClientDao.java            # Client data access
│   │   ├── CourseDao.java            # Course data access
│   │   ├── CartDao.java              # Cart data access
│   │   ├── OrderDao.java             # Order data access
│   │   ├── DaoFactory.java           # DAO Factory
│   │   ├── DaoException.java         # Custom DAO exception
│   │   └── ResultSetMapper.java      # ResultSet mapping utility
│   ├── entity/
│   │   ├── User.java                 # User entity
│   │   ├── Client.java               # Client entity
│   │   ├── Course.java               # Course entity
│   │   ├── Cart.java                 # Cart entity
│   │   ├── CartLine.java             # Cart line entity
│   │   ├── Order.java                # Order entity
│   │   └── OrderLine.java            # Order line entity
│   └── config/
│       ├── DatabaseConfig.java       # Database configuration
│       └── ConfigurationException.java # Configuration exception
├── resources/
│   └── database.properties           # Database configuration file
├── doc/
│ ├── 01-UseCase/
│ │ ├── UseCase.png                   # Use case diagram (PNG)
│ │ └── UseCase.puml                  # Use case diagram (PlantUML)
│ ├── 02-Class/
│ │ ├── Class.png                     # Class diagram (PNG)
│ │ └── Class.puml                    # Class diagram (PlantUML)
│ ├── 03-Sequence/
│ │ ├── 1-OrderSequence.png           # Place order sequence diagram (PNG)
│ │ ├── 1-OrderSequence.puml          # Place order sequence diagram (PlantUML)
│ │ └── TD01-PlaceOrder.png           # Place order textual description
│ ├── 04-MCD/
│ │ └── mcdv3.png                     # Conceptual data model (MCD)
│ ├── SQL/
│ │ └── ScriptSQL.txt                 # SQL creation and insertion scripts
│ └── Specifications.pdf              # Functional specifications document
├── lib/
│   └── mariadb-java-client.jar       # JDBC driver
└── README.md                         # This file
```
## Design Patterns

This project implements several design patterns:

1. **Singleton Pattern**
 - Classes: All DAO classes, DatabaseConfig
 - Purpose: Ensure only one instance exists
 - Implementation: Thread-safe holder pattern

2. **Factory Pattern**
 - Class: DaoFactory
 - Purpose: Centralize DAO instance creation
   
3. **DAO Pattern**
 - Classes: All DAO classes implementing `IDao<T>`
 - Purpose: Abstract database access
 - Benefit: Easy to switch database implementation

4. **MVC-like Pattern**
 - Model: Entity classes
 - Controller: Business classes
 - View: Application layer (console)

## Features Details

### Admin Panel
Administrators have access to a dedicated panel for course management:
- **CRUD operations** on courses (Create, Read, Update, Delete)
- **Validation** of course data before saving
- **Logging** of all administrative actions
- **Security checks** to ensure only admins can modify courses

Access: Login with admin account and select "Admin Panel" from user menu

# UML Diagrams

**Use Case Diagram**
```text
Visitor:
- Browse courses
- Search courses
- Create account

User (extends Visitor):
- Manage cart
- Place order
- View my orders

Admin (extends User):
- Manage courses
```
Key Relationships
- User 1 -- 0..1 Cart: A user can have one cart
- User 1 -- * Order: A user can place multiple orders
- Client 1 -- * Order: A client can have multiple orders
- Order 1 -- * OrderLine: An order contains multiple lines

All UML diagrams are provided in two formats:
- **`.puml`**: PlantUML source code (editable)
- **`.png`**: Generated image (for viewing and documentation)
  
See **doc/** folder for complete UML diagrams

## Code Quality
#### SonarQube Compliance
- Thread-safe Singleton implementation
- Custom exceptions
- Utility classes with private constructors
- No code duplication
- Proper logging

## Troubleshooting

### Database connection issues
- Verify MariaDB service is running
- Check `resources/database.properties` credentials
- Ensure database `training_sales_db` exists
- Verify user `training_user` has proper permissions

### JDBC Driver not found
- Ensure `mariadb-java-client.jar` is in `lib/` folder
- In IntelliJ: Right-click `lib/` → Add as Library

### Application won't compile
- Verify Java JDK 8+ is installed
- Check all dependencies are properly loaded
- Try: File → Invalidate Caches / Restart in IntelliJ

## License

This project is part of a training evaluation and is for educational purposes only
