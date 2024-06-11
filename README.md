**Рішення технічного завдання від Марчука Михайла**

**SWAGGER API: https://app.swaggerhub.com/apis-docs/MISHAAKAMICHAEL999/smida-api-restful/1.0**

**Test coverage:**

![image](https://github.com/marchuk-engineer/smida-techtask/assets/112648286/dfc979c3-25ac-404f-853b-c2a2b94e819d)


***Project is environment secured, INSERT YOUR VALUES INTO .env file***

Endpoints for sign-up, sign-in, logout are permitted to everybody, others require authentication

Endpoints Permissions: Sign-up, Sign-in, and Logout: Permitted for all users.
Other endpoints require authentication.

*Read: User, Editor, Admin
Write: Editor, Admin
Delete: Admin*
**Use the Following Already Registered Users:**

***ADMIN: username: smida-admin, password: admin***

***EDITOR: username: smida-editor, password: editor***

***USER: username: smida-user, password: user***

<details lang="java">
<summary>How to start:</summary>

<details lang="java">
<summary>In Intellij Idea:</summary>




1. Clone project.
2. Run test
(Integration tests are present, but unfortunately, they lack an embedded datasource. Integrating two datasources (SQL and NoSQL) in a single service poses a challenge)
```
mvn test
```
3. Start server
```
mvn clean install -Dmaven.test.skip=true
```

</details>

<details lang="java">
<summary>Docker:</summary>

Clone project and run compose.yaml
```
docker.exe compose -f compose.yaml  up 
```

</details>

<details lang="java">
<summary>Task description:</summary>
**Database Structure:**
Use PostgreSQL to store basic information about companies and their reports.
Use MongoDB to store detailed information about reports (e.g., detailed financial metrics).

**Entities:**
Company:
id (UUID)
name (String)
registration_number (String)
address (String)
created_at (Timestamp)
Report:

id (UUID)
company_id (UUID, foreign key to Company)
report_date (Timestamp)
total_revenue (Decimal)
net_profit (Decimal)
Report Details (stored in MongoDB):

report_id (UUID, foreign key to Report)
financial_data (JSON)
comments (String)

**Functional Requirements:**

Implement CRUD operations for companies and their reports.
Implement an endpoint to retrieve all reports of a specific company.
Implement an endpoint to retrieve detailed information about a report.
Provide database migrations using Liquibase.
Package the service into a Docker container for easy deployment.
Implement authentication and authorization using Spring Security.

**Technologies:**
Java 8-11
Spring
Spring Data JPA
Hibernate
Spring Data MongoDB
Liquibase
Spring Security
Docker
PostgreSQL
MongoDB

**Technical Details:**

The project should be a Maven project.
Write basic unit and integration tests for core operations.
Use Spring MVC to create RESTful web services.
Database configurations should be externalized in application.properties or application.yml.
Provide a Dockerfile to create a Docker image.
Provide a docker-compose.yml for deploying the service with all dependencies.
Code should be hosted in a public repository on GitHub or GitLab with instructions for running.

**Evaluation Criteria:**

Correct implementation of CRUD operations.
Use of appropriate Spring MVC annotations and patterns.
Code quality and structure.
Code coverage with tests.
Proper functioning of Liquibase migrations.
Correct configuration of Dockerfile and docker-compose.yml.
Implementation of authentication and authorization using Spring Security.
Documentation and comments in the code.
Deadline: 5 days.

</details>




