# soccer-cs-prediction-webapp
Prediction web application for soccer championship data.

### data source
Data from [Fußballdaten](http://www.fussballdaten.de) and [Transfermarkt](http://www.transfermarkt.co.uk).

### content
* Dataset is described at [soccer-cs-stats](https://github.com/teeschke/soccer-cs-stats)
* Prediction method described at [soccer-cs-prediction](https://github.com/teeschke/soccer-cs-prediction)

### hosting
* PostgresDB hosted on [heliohost](http://heliohost.org/)
* Web application [heroku](http://heroku.com/)

### access
TBC

### build and run
1. build:
```
mvn package
```

2. run
```
java -jar -Dspring.profiles.active=YOUR-PROFILE target/soccer-cs-prediction-webapp-*.jar
```

Just drop me a mail to get access to the database.