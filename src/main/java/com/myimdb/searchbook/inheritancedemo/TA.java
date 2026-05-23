package com.myimdb.searchbook.inheritancedemo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name="ta_db")
//@PrimaryKeyJoinColumn(name="user_id")
public class TA extends User{
    private int noOfHelpRequest;
}
