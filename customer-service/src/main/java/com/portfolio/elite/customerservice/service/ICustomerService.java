package com.portfolio.elite.customerservice.service;

import com.portfolio.elite.customerservice.v1.model.Customer;

public interface ICustomerService {

    Customer findCustomerById(String id);

}
