package com.portfolio.elite.customerservice;

import jakarta.servlet.Filter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.portfolio.elite.clients.*")
@Slf4j
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(ApplicationContext ctx) {
		return args -> Arrays.stream(ctx.getBeanNamesForType(Filter.class))
			.forEach(bean -> log.info("Filter bean present: {}", bean));
	}
}
