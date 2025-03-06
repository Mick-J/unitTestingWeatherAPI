# Testing Weather REST API  With Wiremock

In this project we :
- create a REST API on weather data source
- test the service part of the REST API using wiremock

#### 1.Weather API call
In the Restcontroller We fetch data from 3 endpoints:
- /current.json  : provide weather data
- /astronomy.json : provide weather data
- /alerts.json  : provide alerts data

curl can be used to fetch data from endpoints:

```
curl -X GET --location "http://localhost:8080/current.json?city=Paris"
curl -X GET --location "http://localhost:8080/alerts.json?city=new+york"
curl -X GET --location "http://localhost:8080/astronomy.json?city=Brussels"
```



All API call by our REST controller were successful as shown below:

<img width="428" alt="rest_api_call" src="https://github.com/user-attachments/assets/0d4039de-3457-4de9-a422-ce0300d2ba32" />


#### 2.Testing whith Wiremock
For testing we mock, GET requests in json file as well as return data to check.
We have tested also 404 response cases for each endpoints.
all tests was successful as shown below:

<img width="331" alt="tests_success" src="https://github.com/user-attachments/assets/b2ad8f39-c57f-4e72-9b95-4fc0f527e675" />


#### 3.Tech Stack
- Java 21
- JUnit 5 (for unit testing)
- WireMock (for mocking external APIs)
- Spring Boot
- Maven(for dependency management)




`Link Weather Service`:
<a href="https://www.weatherapi.com/" title="Free Weather API">WeatherAPI.com</a>

