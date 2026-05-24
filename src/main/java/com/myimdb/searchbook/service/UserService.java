package com.myimdb.searchbook.service;

import java.util.List;

import com.myimdb.searchbook.dto.AdminResponse;
import com.myimdb.searchbook.dto.CreateAdminRequest;
import com.myimdb.searchbook.dto.CreateCustomerRequest;
import com.myimdb.searchbook.dto.CustomerResponse;
import com.myimdb.searchbook.entity.Customer;

public interface UserService {
    CustomerResponse registerCustomer(CreateCustomerRequest request);
    AdminResponse registerAdmin(CreateAdminRequest request);
    List<CustomerResponse> getAllCustomers();
    List<AdminResponse> getAllAdmins();
    Customer getCustomerEntity(Long customerId);
}
