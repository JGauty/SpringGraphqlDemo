# SpringGraphqlDemo


# Getting Started

### Reference Documentation
The project is dockerized spring boot application having graphql api endpoints.

### Prerequisites to build and run the project
* You should have docker running on your computer.
### Steps to build and run the project.

* Build the docker image from source code. If you have maven and java on your machine 
you can manually build the project by running command mvn clean install inside the project folder.
* You can skip the first step as I have already built the jar file and put it into target folder.
* Build docker image - Go inside project folder and run  <br>
  ```docker build -t springgraphqldemo . ```
* Run the docker image using command
  ```docker run --rm --name springgraphqldemo -p 8080:8080 -v ${PWD}/../myfolder/log:/log -d springgraphqldemo```
 
* Graphql endpoint can be found at
   [http://localhost:8080/graphiql?path=/graphql](http://localhost:8080/graphiql?path=/graphql)

* I am using H2 DB to save transactions and static data. It can be explored using
  [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)
  
  Details for DB login
   * Driver class - org.h2.Driver
   * JDBC URL - jdbc:h2:mem:testdb
   * username - sa
   * password - 

### Examples for Graphql usage
* For purchase transaction
```
mutation {
purchase(
    purchaseRequest: {price: "2342", price_modifier: 1, payment_method: "CASH", dateTime: "2022-11-18T16:25:00Z"}) {
    final_price
    points
}
```
