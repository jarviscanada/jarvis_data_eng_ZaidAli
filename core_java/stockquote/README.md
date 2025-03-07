# Stock Quote Application

## Introduction

The Stock Quote Application is a Java-based console program designed to fetch and store real-time stock prices, track stock positions, and analyze financial performance. The application interacts with a **MySQL database** using **JDBC**, follows an **MVC architecture**, and applies **DAO (Data Access Object) and Repository design patterns** for structured data management. The program fetches stock quotes from an external API and allows users to store, retrieve, and analyze stock data efficiently.

## Implementation

### ER Diagram

The database consists of the following key entities:

- **Quote Table**: Stores stock ticker symbols, prices, trading volume, and timestamps.
- **Position Table**: Tracks the number of stocks owned, purchase price, and current value.

The ER Diagram represents the relationships between these entities, ensuring data integrity and efficient querying.

### Design Patterns

The Stock Quote Application follows key design patterns:

#### **DAO (Data Access Object) Pattern**

The DAO pattern separates database logic from business logic by providing an abstraction layer between the application and the database. The DAO classes handle all database operations such as **fetching stock quotes, inserting records, and updating positions**.

#### **Repository Pattern**

The repository pattern further abstracts the database interaction by **decoupling the database logic from the business logic**. It ensures that the service layer only interacts with repository methods rather than making direct queries.

By using these patterns, the application achieves **modularity, maintainability, and scalability**.

## Testing

To ensure data integrity and correctness, the application is tested using **JUnit and Mockito**.

### **Testing Steps**

1. **Database Setup**: A test MySQL database is created with the necessary schema.
2. **Test Data Setup**: Sample stock quotes and positions are inserted into the test database.
3. **Unit Testing**: DAO methods are tested using **JUnit** to verify correct database operations.
4. **Integration Testing**: Service layer tests ensure the correct functioning of business logic by interacting with the database.

By following structured testing, the application ensures reliability and correctness in retrieving and managing stock data.

---

## **Technologies Used**

- **Java**: Core programming language
- **MySQL**: Relational database for storing stock quotes and positions
- **JDBC**: Java Database Connectivity for interacting with MySQL
- **JUnit & Mockito**: Testing frameworks for unit and integration testing
- **Maven**: Dependency management and build automation tool
- **Alpha Vantage API**: External API used to fetch real-time stock quotes

---

## **How to Run the Application**

1. **Setup MySQL Database**:

   - Create a database named `stock_quote`.
   - Execute the provided SQL script to create the necessary tables.

2. **Compile the Project**:
   ```sh
   javac -cp ".;lib/mysql-connector-j-9.2.0.jar;lib/*" -d build src/Main.java src/service/*.java src/dao/*.java src/models/*.java src/util/*.java
   ```
