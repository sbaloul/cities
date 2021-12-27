
# World Cities

It's just a simple application which displays world cities (name and a photo) and allows the user with ALLOW_EDIT role to edit a city. The main technologies used are spring boot and vue.js

## Running the application

### Prerequisites
 - Jdk version 11
 - Git

### Cloning, building and running

To build and run the application please issue the following commands:

~~~
$ git clone https://github.com/sbaloul/cities
$ mvn package
$ java -jar target/cities-0.0.1-SNAPSHOT.jar
~~~

### Default Users

Note all users have the same password **pass**

User names with roles:
- **user**: has ROLE_USER role
- **admin**: has ROLE_ALLOW_EDIT role

