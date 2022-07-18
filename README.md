# prueba_bcopichincha

1) Contruir la Imagen 

sudo docker build -t spring-core:1.0 .

2) Crear el Contenedor

sudo docker run --name corePrueba -d -p 8080:8080 -e DB_HOST=10.1.99.23 -e DB_USERNAME=postgres -e DB_PASSWORD=12345678 spring-core:1.0

3) Las peticionaes se encuentran en la capeta "peticionesPostmna"

4) Documentacion de los api's

http://localhost:8080/swagger-ui
