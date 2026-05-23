package com.myimdb.searchbook.inheritancedemo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name="instructor_db")

public class Instructor extends  User
{
    private int avgRating;
}
