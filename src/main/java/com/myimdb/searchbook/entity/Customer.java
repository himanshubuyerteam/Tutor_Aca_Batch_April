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
@DiscriminatorValue("CUSTOMER")
public class Customer extends BaseUser {

    private String deliveryAddress;
    private Integer loyaltyPoints;

    public Customer(String name, String email, String phone, String deliveryAddress) {
        super(name, email, phone);
        this.deliveryAddress = deliveryAddress;
        this.loyaltyPoints = 0;
    }

    @Override
    public String describeUser() {
        return "Customer account for ordering food";
    }
}
