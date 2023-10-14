package:
	./mvnw clean package -DskipTests

dbuild:
	docker build -t tth/template-service .

drun:
	docker run --name template-service --env-file docker.env -p 8091:8080 tth/template-service
