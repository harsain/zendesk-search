# Zendesk Search

###### Author: Harsain Kapoor (harsain@gmail.com)

---
### Design

Have used Spring Shell framework to create this shell application.
Spring Shell provides infrastructure to create REPL (Read, Evaluate, Print, Loop), allowing us to concentrate on the business logic.

![Relationships](relations.png)

___

#### Available commands:

- ###### Help
````bash
help
````

- ###### Clear
````bash
clear
````

- ###### Quit / Exit (Super important to know how to QUIT)
````bash
quit
````
_OR_
```bash
exit
```

- ###### List
````bash
list
````

- ###### user-search --key --value
````bash
user-search _id 1
````

- ###### organisation-search --key --value
````bash
organisation-search _id 101
````

- ###### ticket-search --key --value
````bash
ticket-search status open
````




----
#### Steps to run the application:
___
###### _Prerequisites_
- Java
- Docker
---

- 1 Clean and install dependencies, will run tests as well
```bash
./mvnw clean install
```

- 2 Compile and package the code
```bash
./mvnw compile package
```

- 3 Run the application
```bash
./mvnw spring-boot:run
``` 
###### _OR_
if you have docker
```bash
docker run -it zendesk-search:0.0.1-SNAPSHOT
```


#### Run Tests

```bash
./mvnw test
```
#### Coverage tests report:
_`open the below mentioned file in a web browser`_

```bash
open target/site/jacoco/index.html
```

#### Generation of Docs
```bash
./mvnw clean javadoc:javadoc
```

#### View Java docs
_`open the below mentioned file in a web browser`_
```bash
open target/site/apidocs/index.html
```
___

#### Scope of further improvements

