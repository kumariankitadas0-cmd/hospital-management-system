# Hospital Management System 🏥

A full-stack, secure, and responsive enterprise web application built to streamline hospital administration. This system manages patient records, doctor directories, and provides secure role-based access for hospital staff.

Developed using **Java Spring Boot**, **Hibernate/JPA**, **MySQL**, and **Thymeleaf** with custom Glassmorphism UI styling.

---

## 🚀 Features
* **Role-Based Access Control (RBAC):** Secure login system powered by Spring Security (Admin vs. Standard Staff).
* **Patient Management:** Full CRUD operations to admit, view, update, and discharge patients.
* **Doctor Directory:** Manage hospital medical staff and assign patients to specific doctors.
* **Modern UI:** Responsive frontend built with Bootstrap 5 and customized with a premium Glassmorphism aesthetic.
* **Automated Data Seeding:** System automatically generates an Admin account on the first launch.

---

## 💻 Tech Stack
* **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA
* **Frontend:** HTML5, Bootstrap 5, Thymeleaf, CSS3
* **Database:** MySQL
* **Build Tool:** Maven

---

## 🛠️ Local Setup Instructions

Follow these steps to get the application running on your local machine using IntelliJ IDEA.

### 1. Prerequisites
Ensure you have the following installed on your computer:
* Java Development Kit (JDK) 17 or higher
* IntelliJ IDEA (Community or Ultimate)
* MySQL Workbench (or any local MySQL server)

### 2. Download the Project
1. Click the green **Code** button at the top of this repository.
2. Select **Download ZIP**.
3. Extract the downloaded ZIP file to a folder on your computer.

### 3. Database Configuration
Before running the application, you need to set up your local database.
1. Open **MySQL Workbench**.
2. Run the following SQL command to create an empty database:
   ```sql
   CREATE DATABASE hospital_system;
   ```
> **Note:** The application is pre-configured to connect to `localhost:3306` with the default MySQL username `root` and password `root`. If your local MySQL credentials are different, update them in `src/main/resources/application.properties`.

### 4. Open and Run in IntelliJ IDEA
1. Open **IntelliJ IDEA**.
2. Click **File > Open** and select the extracted project folder.
3. Wait for IntelliJ to download all Maven dependencies (you will see a progress bar at the bottom right).
4. Locate the main application class: `src/main/java/com/company/hospitalsystem/HospitalSystemApplication.java`.
5. Click the green **Play/Run** button next to the main method to start the server.

---

## 🔐 Accessing the Application

Once the terminal shows `Tomcat started on port 8080`, the application is live!

Open your web browser and navigate to:
👉 **http://localhost:8080**

### 🧑‍💼 Admin Login (Default)
On the first launch, the system automatically creates a master Admin account. Use these credentials to access the full dashboard:

| Field    | Value               |
|----------|---------------------|
| Email    | admin@hospital.com  |
| Password | admin123            |

> Admins have exclusive rights to discharge/delete patient records and manage the doctor directory.

### 👩‍⚕️ Standard Staff Access
For normal users (nurses, receptionists):
1. Click **"Need an account? Sign up!"** on the login screen.
2. Register a new account.
3. The system will automatically assign the `ROLE_USER` permission, allowing them to admit patients and view records without delete privileges.

---

*Designed and developed by ANKITA DAS*