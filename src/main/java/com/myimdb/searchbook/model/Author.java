package com.myimdb.searchbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    String name;
    int age;


    public Author()
    {
        System.out.println("Bean of Author is Created");
    }

}
//Hibernate: create table author
//        (id bigint not null auto_increment,
//         age integer not null,
//         name varchar(255),
//primary key (id))
//
//
//engine=InnoDB
