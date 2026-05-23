package com.myimdb.searchbook.inheritancedemo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name="mentor_db")
//@PrimaryKeyJoinColumn(name="user_id")
public class Mentor extends User
{
    private String companyName;
}
