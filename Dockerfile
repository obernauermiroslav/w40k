FROM gradle:jdk17

WORKDIR /src

COPY . .
RUN ./gradlew clean build

CMD ./gradlew bootRun

