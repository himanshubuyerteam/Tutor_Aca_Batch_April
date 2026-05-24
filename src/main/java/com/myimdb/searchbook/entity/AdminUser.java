package com.myimdb.searchbook.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class AdminUser extends BaseUser {

    private String supportLevel;

    public AdminUser(String name, String email, String phone, String supportLevel) {
        super(name, email, phone);
        this.supportLevel = supportLevel;
    }

    @Override
    public String describeUser() {
        return "Admin account for monitoring restaurants and orders";
    }
}
