package com.myimdb.searchbook.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Author {
    String name;
    int age;


    public Author()
    {
        System.out.println("Bean of Author is Created");
    }

}
