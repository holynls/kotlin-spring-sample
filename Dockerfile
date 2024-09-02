FROM amazoncorretto:21-alpine

COPY . /usr/src/scheduleApp
WORKDIR /usr/src/scheduleApp

RUN chmod +x ./gradlew

EXPOSE 8080

RUN ./gradlew build


