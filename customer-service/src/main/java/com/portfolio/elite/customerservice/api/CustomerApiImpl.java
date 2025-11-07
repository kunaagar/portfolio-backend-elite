package com.portfolio.elite.customerservice.api;

import com.portfolio.elite.customerservice.service.ICustomerService;
import com.portfolio.elite.customerservice.v1.api.CustomersApi;
import com.portfolio.elite.customerservice.v1.model.Customer;
import com.portfolio.elite.customerservice.v1.model.CustomerCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerApiImpl implements CustomersApi {

    private final ICustomerService customerService;

    @Override
    public ResponseEntity<Customer> createCustomer(CustomerCreateRequest customerCreateRequest) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(String id) {
        log.debug("Get customer by id {}", id);
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }
}
