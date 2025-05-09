# Spring Microservice Application Example

A small scale spring microservice application example. Application can run on localhost, docker & kubernates.  

## How To Execute?

Change directory to root of the project and run the below commmand.

### Docker

```bash
docker compose up
```

### Kubernatess

```bash
kubectl apply -k kustomization/
```

## Services

- Movie Service
- Artist Service
- Authentication Service (JWT)
- API Gateway
- Eureka / Service Registry

## Eureka & Swagger URLs

### Localhost & Docker

- Eureka - http://localhost:8090/eurekawebui
- Swagger - http://localhost:8090/swagger-ui.html

### Kubernates

For kubernates we need to get the api-gateway service NodePort IP or External/LB IP.

- Eureka - http://\<API-GATEWAY\>:8090/eurekawebui
- Swagger - http://\<API-GATEWAY\>:8090/swagger-ui.html

#### For Minikube 

```bash
minikube service api-gateway 
```

## Authentication

The `user & password` needed to access above urls are mentioned in `application.yml file in api-gateway service / docker compose file / inside kustomization directory`. Default credential - user & password.
