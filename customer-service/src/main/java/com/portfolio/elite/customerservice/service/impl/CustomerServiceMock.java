package com.portfolio.elite.customerservice.service.impl;


import com.portfolio.elite.clients.profileservice.v1.model.GetProfileResponse;
import com.portfolio.elite.customerservice.provider.ProfileServiceProvider;
import com.portfolio.elite.customerservice.service.ICustomerService;
import com.portfolio.elite.customerservice.v1.model.Customer;
import com.portfolio.elite.customerservice.v1.model.CustomerProfileInfo;
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

    private final ProfileServiceProvider profileServiceProvider;

    @Override
    public Customer findCustomerById(String id) {
        //TODO :: handle case when profile response is not available
        final var profileResponse = profileServiceProvider.getProfileById(id).orElseGet(GetProfileResponse::new);
        return new Customer().id("cust-001").name("customer-001").email("cust@mock.com")
            .profileInfo(new CustomerProfileInfo().gender(profileResponse.getGender())
                .age(profileResponse.getAge()).dob(profileResponse.getDob()));
    }

    @PostConstruct
    protected void log() {
        log.debug("CustomerService is initialised in mock mode");
    }
}
