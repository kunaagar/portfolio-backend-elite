package com.portfolio.elite.customerservice.service.impl;


import com.portfolio.elite.clients.profileservice.v1.api.ProfilesApiClient;
import com.portfolio.elite.customerservice.service.ICustomerService;
import com.portfolio.elite.customerservice.v1.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("mock")
@RequiredArgsConstructor
public class CustomerServiceMock implements ICustomerService {

    private final ProfilesApiClient client;

    @Override
    public Customer findCustomerById(String id) {
        final var result = client.getProfileById("cust-001");
        log.debug("Profile service response: {}", result);
        return new Customer().id("cust-001").name("customer-001").email("cust@mock.com");
    }

    @PostConstruct
    protected void log() {
        log.debug("CustomerService is initialised in mock mode");
    }
}
