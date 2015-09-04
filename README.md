#Shoe Store With Spark Many-to-Many and postgres/psql
<h3>Author:</h3>
Jocelyn Alsdorf

<h3>Description:</h3>
This app allows a user to enter and manage shoe brands and the stores that carry them. Crud funtionality available for both clients and stylists. Data is persisted via postgres/psql. Joing table and join statements used for many-to-many relationships between brand and stores 


<h2>Setup instructions:</h2>
Dependencies:
Spark
Gradle
Java 
Testing Suite:
FluentLenium
JUnit
---------
In PSQL:
CREATE DATABASE shoe_stores;

CREATE TABLE stores (id serial PRIMARY KEY, name varchar);

CREATE TABLE brands (id serial PRIMARY KEY, description varchar);

CREATE TABLE stores_brands (id serial PRIMARY KEY, store_id int, brand_id int);


<h2>Copyright</h2>
MIT License. Copyright 2015  Jocelyn Alsdorf