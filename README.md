# Mail Authentication Application
### Project Description
Welcome to the Mail Authentication Application! This application provides a simple yet secure mechanism for user authentication using email verification and one-time passwords (OTPs).
### Features
- **User Signup:** Create a new user account with a username, email, and password.
- **Email Verification:** Users receive a one-time password (OTP) for account verification.
- **OTP Regeneration:** Regenerate OTPs for user accounts if needed.
- **User Login:** Authenticate users using their email and password.
 ### Prerequisites
* Java Development Kit (JDK) installed
* Maven installed
* An email account for sending OTPs (e.g., Gmail)
### Getting Started
**Step 1: Clone the Repository**
```bash
git clone https://github.com/your-username/mail-authentication-app.git 
```
 Clone the Git repository to your local machine. This will download the source code and set up the project for you.
 
**Step 2: Configure Email Credentials**

Update the ***src/main/resources/application.properties*** file with your email credentials. This includes specifying your SMTP server details, such as host, port, username, and password. Make sure to replace the placeholders with your actual email information.
```bash
spring.mail.host=your-smtp-server
spring.mail.port=your-smtp-port
spring.mail.username=your-email@example.com
spring.mail.password=your-email-password
```
**Step 3: Build and Run the Application**

Build and run the Spring Boot application. This command compiles the source code, resolves dependencies and start the server.

**API Endpoints**

**1. Create a New User Account (Signup)**

  Endpoint:
```bash
POST /api/user/signup

```
Description:

Create a new user account by providing signup information.

Request Body:
```bash
{
  "username":"username",
  "email": "user@example.com"
  "password":"password"
}
```
**2. Verify User Account with OTP**
Endpoint:
```bash
PUT /api/user/otp/verify
```
Description:

Verify a user account by providing the email and OTP (One-Time Password) received during signup.

Request Body:
```bash
{
 PUT /api/user/otp/verify?email=john@example.com&otp=123456

}
```
**3. Regenerate OTP for User Account**

Endpoint:
```bash
PUT  /api/user/otp/generate

```
Description:

Regenerate a new OTP for a user account.

Request Body:
```bash
PUT /api/user/otp/generate?email=john@example.com
```
**4. User Login**

Endpoint:
```bash
 POST /api/user/login

```
Description:

Authenticate a user by providing login credentials.

Request Body:
```bash
{
"email": "john@example.com",
"password": "secure_password"
}
```


