RESTful BigData Analytics Service
======================

$ mvn clean package

$ java -jar target/bigdata-0.0.1-SNAPSHOT.jar server config/dev_config.yml 

# How to run this Java process forever
$ nohup ./bin/dev.sh 0<&- &> /tmp/app.log &

Service endpoint: http://54.215.165.222:8080/bigdata/v1

Admin: http://54.215.165.222:8081

