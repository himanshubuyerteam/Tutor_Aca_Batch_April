package com.myimdb.searchbook.service.impl;

import com.myimdb.searchbook.exception.BadRequestException;

public abstract class AbstractUserRegistrationService {

    protected void validateCommonUserFields(String name, String email, String phone) {
        if (isBlank(name) || isBlank(email) || isBlank(phone)) {
            throw new BadRequestException("Name, email, and phone are required");
        }
        if (!email.contains("@")) {
            throw new BadRequestException("Email must contain @");
        }
    }

    protected boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
