package com.myimdb.searchbook.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.myimdb.searchbook.dto.AdminResponse;
import com.myimdb.searchbook.dto.CreateAdminRequest;
import com.myimdb.searchbook.dto.CreateCustomerRequest;
import com.myimdb.searchbook.dto.CustomerResponse;
import com.myimdb.searchbook.entity.AdminUser;
import com.myimdb.searchbook.entity.Customer;
import com.myimdb.searchbook.exception.BadRequestException;
import com.myimdb.searchbook.exception.ResourceNotFoundException;
import com.myimdb.searchbook.repository.AdminUserRepository;
import com.myimdb.searchbook.repository.CustomerRepository;
import com.myimdb.searchbook.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractUserRegistrationService implements UserService {

    private final CustomerRepository customerRepository;
    private final AdminUserRepository adminUserRepository;

    @Override
    public CustomerResponse registerCustomer(CreateCustomerRequest request) {
        validateCommonUserFields(request.getName(), request.getEmail(), request.getPhone());
        if (isBlank(request.getDeliveryAddress())) {
            throw new BadRequestException("Delivery address is required for customer");
        }

        Customer customer = new Customer(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getDeliveryAddress());

        Consumer<Customer> initializePoints = item -> item.setLoyaltyPoints(10);
        initializePoints.accept(customer);

        return toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public AdminResponse registerAdmin(CreateAdminRequest request) {
        validateCommonUserFields(request.getName(), request.getEmail(), request.getPhone());
        if (isBlank(request.getSupportLevel())) {
            throw new BadRequestException("Support level is required for admin");
        }

        AdminUser adminUser = new AdminUser(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getSupportLevel());

        return toAdminResponse(adminUserRepository.save(adminUser));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            responses.add(toCustomerResponse(customer));
        }
        return responses;
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        List<AdminResponse> responses = new ArrayList<>();
        for (AdminUser adminUser : adminUserRepository.findAll()) {
            responses.add(toAdminResponse(adminUser));
        }
        return responses;
    }

    @Override
    public Customer getCustomerEntity(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .deliveryAddress(customer.getDeliveryAddress())
                .loyaltyPoints(customer.getLoyaltyPoints())
                .userTypeDescription(customer.describeUser())
                .build();
    }

    private AdminResponse toAdminResponse(AdminUser adminUser) {
        return AdminResponse.builder()
                .id(adminUser.getId())
                .name(adminUser.getName())
                .email(adminUser.getEmail())
                .phone(adminUser.getPhone())
                .supportLevel(adminUser.getSupportLevel())
                .userTypeDescription(adminUser.describeUser())
                .build();
    }
}
