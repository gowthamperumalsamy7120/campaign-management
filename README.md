****Email Campaign Management System – Spring Boot Application****

A simple Email Campaign Management System built using Spring Boot, Java 17, and PostgreSQL.
The application allows administrators to create email campaigns, manage recipients, send campaigns, and track delivery statistics via a dashboard.

****Features****

**Campaign Management**

Create and manage email campaigns with subject, content, scheduled time, and status.

**Recipient Management**

Add recipients individually or upload them in bulk using Excel files.

**Campaign Execution**

Execute a campaign manually and send emails to all subscribed recipients.

**Delivery Tracking**

Track each email delivery status (Sent / Failed).

**Dashboard**

View campaign statistics including:

1. Total Recipients
2. Sent Count
3. Failed Count
4. Campaign Status

## Accessing the Application

After running the application, it will be available at: http://localhost:8080

**Bulk Upload**

Upload multiple recipients using an Excel file.

**Sample Excel File**

A sample Excel file is provided in the repository to demonstrate the expected format for bulk recipient upload.

Location in the project:

[excel-file/sample-recipients.xlsx]()

**Swagger UI**

http://localhost:8080/swagger-ui/index.html
Interactive API documentation and testing using Swagger.

**Clean Architecture**

Follows standard layered architecture:

1. Controller
2. Service
3. Repository
4. DTO
5. Entity

**Technologies Used**

1. Java 17
2. Spring Boot 3.2
3. Spring Web
4. Spring Data JPA
5. PostgreSQL
6. ModelMapper
7. Lombok
8. SpringDoc OpenAPI (Swagger UI)
9. Apache POI
10. Maven


### Running the Application

Using Maven:
mvn spring-boot:run


docker build -t gowthamperumalsamy/campaign-management:latest .
docker push gowthamperumalsamy/campaign-management:latest 