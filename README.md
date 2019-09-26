# demo-mongoDB
An example of how how to integrate Hibernate OGM into spring boot application with Docker

Programming language: Java
https://www.java.com/ru/

Spring Boot
https://spring.io/projects/spring-boot

ORM/OGM System: Hipernate OGM
https://docs.jboss.org/hibernate/stable/ogm/reference/en-US/html_single/?v=5.4 . https://hibernate.org/ogm/

Database: mongodb
https://docs.mongodb.com/

### Start project:
run file
```unux
./start.sh
```
## REST API:
### Create a new product  
```unix
curl -d '{"phoneName":"phone1","phoneDescription":"description","parameters":{"параметр":"значение"}}' -H 'Content-Type: application/json' http://localhost:8090/v1/phone/
```

### Get a list of product names, with the ability to filter by:
a) name 
```unix
curl -v http://localhost:8090/v1/phone/?name=HUAWEI
```
b) the selected parameter and its value 
```unix
curl "http://localhost:8090/v1/phone/filter/?parameter=аккумулятор&value=3500 мАч"
```

### Get product details by ID
1. find all phones and copy ID
```unix
curl -v http://localhost:8090/v1/phone
```
2. Paste ID in {id}
```unix
curl -v http://localhost:8090/v1/phone/{id}
```

### Check controller
```unix
curl -v http://localhost:8090/v1/ping 
```
 
For tests, change value in property name="hibernate.ogm.datastore.host" on localhost
And stop your local mongo, because I used EmbeddedMongo from Springdata.


