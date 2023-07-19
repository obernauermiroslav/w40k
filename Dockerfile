FROM gradle:jdk17

WORKDIR /src

COPY . .

RUN gradle clean build

CMD gradle bootRun



