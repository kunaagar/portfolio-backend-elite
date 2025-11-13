# Week 1 - Day 3 Summary

- Api first approach - Drafted API specifications for profile service with an API `Get Profile by ID`
- Using openapi generator plugin, generated api interfaces and model packages
- Enabled mock profiles on both `customer-service` and `profile-service` until the database is integrated.
- Using openapi generator plugin and with help of spring cloud openfeign client dependency, feign-clients are generated and can be used to invoke intended API's e.g `profile-service` in our case.
- Service using feign client to call another api, should add `@EnableFeignClients(basePackages = "com.portfolio.elite.clients.*")` config to register feign clients
- Observability kata to measure the runtime behavior using `@Timed` annotation which will then generate custom metrics e.g `feign.call.profile.api`
- Note these annotations works only on the methods invoked by a bean and not on a method invoked by another method in same class.
- Verified metrics under `/actuator/prometheus` endpoint.