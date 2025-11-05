# Feynman Task - Customer Service + Observability

### Microservice Architecture
> A microservice is a small, independent service thats performs its specific operation with its own business logic and data. Each microservice has its own process, communicates with other services via lightweight API's like Restful API's and messaging systems like Kafka over a network using HTTP protocol.
> With this architecture, independent microservices can be developed, deployed and scaled independently.

### Micrometer & Prometheus
- **Micrometer** - enables us to collect metrics from JVM and application and makes them available to monitoring systems.
- **Prometheus** - a time-series database which scrapes metrics using actuator endpoint, at regular intervals and stores them for analysis and alerting purposes.
- Enabling ***prometheus*** on spring-boot application exposes `/actuator/prometheus` endpoint that prometheus can use to scrape metrics.
- Developers can build custom metrics (counters, timers and gauges) using micrometer to measure different data points.
- Together, Micrometer and Prometheus enable observability by turning runtime behavior into measurable data that can be visualised in tools like Grafana and used for performance tuning or anomaly detection.
- `management.endpoints.web.exposure.include=health,info,prometheus` configuration needs to be added in `application.yaml` of `customer-service` to enable /actuator/prometheus` endpoint.


  
