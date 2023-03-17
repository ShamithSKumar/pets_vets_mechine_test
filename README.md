# pets_vets_mechine_test

Basics
-------
The application is created from spring initializer
Java version used 17

Additional Dependencies used
-------------------------
Lombok,
Spring security

To setup the Lombok please download Lombok from this link
https://projectlombok.org/download

Install the Lombok on the same directry where your Id is installed

Configure the database
----------------------
DB used Mysql, download and install Mysql latest
Create database "pets_vets_db" 
You can create database as a root user or as the logined user which you created

Go to application.properties file in the project
you can see the db configurations- edit as needed

DB config from application.properties
---------------------
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/pets_vets_db
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql: true
 
Steps to run the application
----------------------------
Once all the above configs are done do a "maven clean install"/ "maven update project"
All the dependencies will get downloaded.
After all the dependencies added try to run the application as "Spring boot app" (Right click on the project from ide -> Run As -> Spring boot app)
This will create the required tables as well.

The Angular configurations are provided in Angular project Readme file.