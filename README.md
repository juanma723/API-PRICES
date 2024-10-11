
# Prueba técnica Inditex

Api Price 


## Tech Stack


Java 23, Spring boot 3, Groovy con Spock para los test


## Instalación


Con IntelliJ se está usando el JRE openjdk-23, apache-maven-3.9.9 y utilizar la opcion de enable annotion processing en las opciones


Desde la raiz del proyecto
```bash
  mvn clean package
```

Si el IDE no te reconoce las clases generadas por OpenAPI, click derecho en el pom.xml -> Maven -> Reload project 



## Arrancar en local

Docker

Arrancar docker-compose, asegurarse de tener el servicio docker iniciado 

```bash
  docker-compose up --build
```
## Plugin Spring boot

Tambien se puede usar este comando de maven sustituyendo a docker
```bash
  mvn spring-boot:run
```

## Ejecutar tests

Tienen test unitarios como integrados
```bash
  mvn test
```
## Swagger

http://localhost:8080/swagger-ui/index.html
## Ejemplo llamada 


curl -X 'GET' \
  'http://localhost:8080/api/v1/prices?time=10:00&day=2020-06-14&productId=35455&brandId=1' \
  -H 'accept: application/json'

