# docker-swarm

This project is meant to be an example of how you can deploy an application using a microservices architecture using docker swarm and docker compose. This project is not intended to explain Spring, Spring Boot or any of the other technologies included, therefore, there will be limited comments and no unit tests provided. The emphasis is on the docker related components.
## Application Overview
The sample application built for this example is a stock ticker that randomly generates stock values on a periodic basis for a few well known stocks. Every time a stock value is calculated, it is logged in our time series database and sent to a rabbitmq server for anyone listening.
## Services
There are 6 services that will be deployed to support the application described above.

1. stockticker - A spring boot service that generates stock values and sends them out for processing
2. dbservice - A spring boot service that accepts new stock values and logs them to a time series database.
3. cacheservice - A spring boot application that listens for new stock values via RabbitMQ and caches them.
4. RabbitMQ
5. Redis
6. InfluxDB

## Building the docker images
After building the applications, each maven module will have its own deployment directory inside of its target directory. Using a command line, go to the following directories:
1. stockticker: docker_swarm/stockticker/target/stockticker-1.0
2. dbservice: docker_swarm/dbservice/target/dbservice-1.0
3. cacheservice: docker_swarm/cacheservice/target/cacheservice-1.0

From each directory, run the following command:
$ docker build -t [tag_name] .

If all three images build successfully then you can use docker compose and docker stacks.

## Docker Compose
Using docker compose will deploy all of the services on one machine. The docker compose file can be found at docker_swarm/stockticker/target/stockticker-1.0/compose after building. Run this compose file with the following command:

$ docker-compose up

## Docker Swarm Stack

Before deploying to a swarm, you must create a swarm.