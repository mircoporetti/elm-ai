.PHONY: all db build run test

all: db build run

db:
	@echo "Starting Docker Compose..."
	docker-compose up -d

build:
	@echo "Sourcing .env.local and building the app (skip tests)..."
	set -a && source .env.local && set +a && ./mvnw package -DskipTests

run:
	@echo "Running the Spring Boot app..."
	set -a && source .env.local && set +a && java -jar target/elm-ai.jar

test:
	@echo "Sourcing .env.local and running tests..."
	set -a && source .env.local && set +a && ./mvnw test