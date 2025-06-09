# ✈️ Airport App

A Spring Boot-based airport management system with a MySQL backend. This app supports airplane, flight, and user management with a modular and extendable structure.

---

## 🔧 Getting Started

Follow the steps below to configure and run the project locally.

---

## 🗄️ Step 1: Set Up the MySQL Database

Make sure you have **MySQL** installed and running on your local machine.

### 📂 Run the SQL scripts in the following order:

1. **Create the database**
   ```sql
   source create_database.sql;
   
2. **Apply the initial schema**
    ```sql
   source airport_app_scheme_v1.1.sql;
   
3. **Create dependent tables**
    ```sql
   source airport_app_dependent_table.sql;

4. **Apply schema updates**
    ```sql
   source airport_app_database_scheme_v1.2.sql;

5. **Insert sample dataset**
    ```sql
   source airport_app_dataset.sql;
You can run these in MySQL Workbench or the CLI:

````sql
    mysql -u root -p airport_app < create_database.sql
    mysql -u root -p airport_app < airport_app_scheme_v1.1.sql
    mysql -u root -p airport_app < airport_app_dependent_table.sql
    mysql -u root -p airport_app < airport_app_database_scheme_v1.2.sql
    mysql -u root -p airport_app < airport_app_dataset.sql
````

### 🚀 Running the Application

Make sure you have:

- Java 17+ installed
- Maven (or use the Maven wrapper ```./mvnw```)

Run the app:

```bash
    ./mvnw spring-boot:run
```


The app will start at:
📍 http://localhost:8081/

### 🚀 Running the Application
The following properties are defined in ```application.properties:```

````bash
    spring.application.name=airport-app
    server.port=8081
    
    # Define Database Configurations
    spring.datasource.url=${MYSQL_URL:jdbc:mysql://localhost:3306/airport_app}
    spring.datasource.username=${DATABASE_USERNAME:root}
    spring.datasource.password=
    
    # Thymeleaf View Resolver
    spring.thymeleaf.prefix=classpath:/templates/
    spring.thymeleaf.suffix=.html
````

### 🧪 Testing

To test API endpoints, you can use:

- Postman

- curl

- Browser for UI-based features (Thymeleaf templates)

### 📁 Folder Structure

````css
    src
    └── main
        ├── java/...       → Java source code
        └── resources
            ├── templates/ → Thymeleaf templates
            ├── static/    → CSS, JS, images
            └── application.properties
````

### 🧑‍💻 Author
Maintained by ````Deshan Vimukthi (GSCOMP 325)````. Contributions and improvements welcome!






