# PortfolioManager

How to start the PortfolioManager application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/portfolio-manager-0.1-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


How to run locally
---
1. launch local mongodb instance:   *mongod --dbpath=<path to db dir>*


1. launch app server in test mode  