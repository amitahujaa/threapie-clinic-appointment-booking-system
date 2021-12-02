# threapie-clinic-appointment-booking-system

This is standalone spring boot(embedded tomcat server and embedded H2 In-Memory Database) service and can be run directly using following command 

## First we Build the project using 
 
```bash
mvn clean install
```

This will run all unit tests and generate all binaries and standalone executable jar file

## Run generated standalone jar file
```bash
java -jar ./target/threapie-clinic-appointment-booking-system-1.0-SNAPSHOT.jar
```


## This is travis managed fully automated deployment project which build docker images and push to AWS - EC2 (Amazon Web Services)
#### If you push change to this project it will start Travis build job:- [Travis Builds](https://travis-ci.com/github/amitahujaa/threapie-clinic-appointment-booking-system/builds)
#### This build will also create docker image and push docker image to artifactory: [Docker images](https://hub.docker.com/repository/registry-1.docker.io/amit1704/threapie-clinic-appointment-booking-system/tags?page=1&ordering=last_updated)

#### AWS deployed link to test service :- [AWS Service URL](http://ec2-18-222-36-25.us-east-2.compute.amazonaws.com:8080/bookings)

### To test AWS service use following links:

```URL
http://ec2-18-222-36-25.us-east-2.compute.amazonaws.com:8080
```

#### GET All Bookings
```REST
GET: /bookings
```
#### GET one Booking
```REST
GET: /bookings/{id}
```
#### Create New Booking

```REST
POST: /bookings
    {
        "customerId": "ab123",
        "serviceId": "ser100",
        "clinicId": "clinic100",
        "date": "2005-12-01",
        "startTime": "08:32:00"
    }
```

#### success response with status PASSED code 201 CREATED
```REST
POST: /bookings
    {
        "customerId": "ab123",
        "serviceId": "ser100",
        "clinicId": "clinic100",
        "date": "2005-12-01",
        "startTime": "08:32:00"
    }
```

### Rules Implemented for post api:

#### Verify that clinicId, customerId, and serviceId exist otherwise we will show following error message with status FAILED and code 400 BAD REQUEST

![image](https://user-images.githubusercontent.com/17794499/111906414-657e8180-8a48-11eb-969e-03669190cc60.png)

![image](https://user-images.githubusercontent.com/17794499/111906134-0409e300-8a47-11eb-8e70-2be8592e7fe4.png)

![image](https://user-images.githubusercontent.com/17794499/111906165-2e5ba080-8a47-11eb-9987-17a56d37b1c2.png)

#### If above validations passed we will check for requested timeslots available if not we will show following error message with status FAILED and code 400 BAD REQUEST 

![image](https://user-images.githubusercontent.com/17794499/111906231-7b3f7700-8a47-11eb-9ad8-be619c045cbe.png)


#### Now we check for overlap booking service if overlapping we will show following error message with status FAILED and code 400 BAD REQUEST

#### If exactly same reques 

![image](https://user-images.githubusercontent.com/17794499/111906283-b346ba00-8a47-11eb-9d4e-18f539ceb07c.png)

#### If start or end time overlap

![image](https://user-images.githubusercontent.com/17794499/111906344-fdc83680-8a47-11eb-950b-b878fb116ba5.png)

#### To Delete Booking
```REST
Delete: /bookings/{id}
```
