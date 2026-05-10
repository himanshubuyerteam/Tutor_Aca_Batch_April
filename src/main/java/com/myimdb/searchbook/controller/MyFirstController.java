package com.myimdb.searchbook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fc")
public class MyFirstController {


    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String getHello()
    {
        return "Hello";
    }

    @RequestMapping(value = "/val", method =  RequestMethod.GET)
    public int getNumber()
    {
        return 10;
    }

    @GetMapping("/getUser")
    public String getUser(
            @RequestParam(name = "firstname") String firstName,
            @RequestParam(name = "id") int Id,
            @RequestParam(name = "lastName", required = false) String lastName
    )
    {
        return "Returning User of Name "+firstName+" id is "+Id+ " "+lastName;
    }

    @GetMapping("/getId/{id}")
    public String getIncreasedId(
       @PathVariable(value = "id") int id
    )
    {
        int newVal = id+10;
        return "newVal is "+newVal;
    }
}
