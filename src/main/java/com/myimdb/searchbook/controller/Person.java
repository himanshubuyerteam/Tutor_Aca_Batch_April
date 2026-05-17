package com.myimdb.searchbook.controller;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class Person {
    int id;
    String name;

    Person(){}
    Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    void printName()
    {
        System.out.println(name);
    }
}
