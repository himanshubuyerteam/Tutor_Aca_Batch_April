package com.myimdb.searchbook.controller;

import java.util.List;

import com.myimdb.searchbook.dto.AdminResponse;
import com.myimdb.searchbook.dto.CreateAdminRequest;
import com.myimdb.searchbook.dto.CreateCustomerRequest;
import com.myimdb.searchbook.dto.CustomerResponse;
import com.myimdb.searchbook.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/customers")
    public CustomerResponse registerCustomer(@RequestBody CreateCustomerRequest request) {
        return userService.registerCustomer(request);
    }

    @PostMapping("/admins")
    public AdminResponse registerAdmin(@RequestBody CreateAdminRequest request) {
        return userService.registerAdmin(request);
    }

    @GetMapping("/customers")
    public List<CustomerResponse> getAllCustomers() {
        return userService.getAllCustomers();
    }

    @GetMapping("/admins")
    public List<AdminResponse> getAllAdmins() {
        return userService.getAllAdmins();
    }
}
