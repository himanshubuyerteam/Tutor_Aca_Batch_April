package com.myimdb.searchbook.model;


import jakarta.persistence.*;

@Entity(name="product_db")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String description;
    String imageURL;
    double price;
    @ManyToOne
    Category category;


    Long getId()
    {
        return id;
    }
    String getTitle()
    {
        return title;
    }
    String getDescription()
    {
        return description;
    }
    void setDesc(String description)
    {
        this.description = description;
    }
}
