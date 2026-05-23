package com.myimdb.searchbook.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="product_db")
public class Product extends BaseModel{

    String title;
    String description;
    String imageURL;
    double price;
    @ManyToOne
    Category category;

}
