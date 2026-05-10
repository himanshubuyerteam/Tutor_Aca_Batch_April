package com.myimdb.searchbook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sc")
public class SecondController {


//    @RequestMapping(value = "/first", method = RequestMethod.GET)
    @GetMapping("/first")
    public String getHello()
    {
        return "Bye";
    }

    @RequestMapping(value = "/val", method =  RequestMethod.GET)
    public int getNumber()
    {
        return 20;
    }
}
