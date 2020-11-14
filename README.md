# Recorded Crimes API 

Its Spring boot REST API which returns list of recorded crimes for premier league football stadiums sorted by year and month

GET /premier-league/football-stadium/recoredcrimes

Returns the list of recorded crimes for premier-league football stadium. 

GET /premier-league/football-stadium/recoredcrimes?date={YYYY-MM}

Returns the list of recorded crimes for premier-league football stadium for given date.

Instructions to run the application:

1. Download the project
2. mvn clean install
3. Run the FootballStadiumRecordedCrimeApplication.
Then make a query to the recorded crimes service API using postman or curl request. 

Example to query in postman: 

GET /localhost:8080/premier-league/football-stadium/recoredcrimes

GET /localhost:8080/premier-league/football-stadium/recoredcrimes?date=2018-08


Swagger API Documentation

http://localhost:8080/v2/api-docs

Swagger UI 

http://localhost:8080/swagger-ui.html#/football-stadium-controller








    

