# ELM AI Assistant

Hey! I am your favorite ELM Swiss Assistant!

I can help explaining or just looking for info about ELM Salaries Standards.

## Requirements

- Java 21
- Docker
- OpenAI and HuggingFace accounts

## How to run the app

Copy .env.example file to .env.local and set all the variables

Then:

### Option 1 - Makefile

Run the following command

    make

Congrats! Simple but effective! :) 

### Option 2

Run the database:

    docker compose up

Build the app

    set -a; source .env.local; ./mvnw package

and run it

    java -jar target/elm-ai.jar

## How to run the tests

Ensure the app was previously built and then

    make test

or

    set -a && source .env.local && set +a && ./mvnw test
