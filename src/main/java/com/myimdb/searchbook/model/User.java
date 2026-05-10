//package com.myimdb.searchbook.model;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.stereotype.Component;
//
//@Component
//public class User {
//
//    String name;
//    int age;
//
//    public User()
//    {}
//    public User(String name, Integer age)
//    {
//        this.name = name;
//        this.age = age;
//        System.out.println("User Constructor is Invoked");
//    }
//    @PostConstruct
//    void printAfterCreation()
//    {
//        System.out.println("Bean of User is Created");
//    }
//    @PreDestroy
//    void printBeforeDestory()
//    {
//        System.out.println("Bean of User is Destroyed");
//    }
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//    public void setAge(int age)
//    {
//        this.age = age;
//    }
//
//    public String getName()
//    {
//        return this.name;
//    }
//    public int getAge()
//    {
//        return this.age;
//    }
//
//
//}
