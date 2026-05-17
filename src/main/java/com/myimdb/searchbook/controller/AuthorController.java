package com.myimdb.searchbook.controller;


import com.myimdb.searchbook.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("/ac")
public class AuthorController {

    Map<Integer, Book> bookdb = new HashMap<>();

    public AuthorController() {
        bookdb.put(1,new Book(1,"Crack Interview atFAANG","Richa Tayal",100.0));
        bookdb.put(2,new Book(2,"Head First with Java","Mohan",200.0));
    }

    @GetMapping("/getAllBooks")
    public Collection<Book> getAllBooks(){
        return bookdb.values();
    }

    @GetMapping("/hello")
    public String getHello()
    {
        System.out.println("Inside Function");
        return "Hello Guys";
    }

    @GetMapping("/bye")
    public String getBye()
    {
        System.out.println("Inside Function");
        return "Bye Guys";
    }

    @GetMapping("/namaste")
    public String getNamaste()
    {
        System.out.println("Inside Function");
        return "Namaste Guys";
    }

    @GetMapping("/getParticularBook/{id}")
    public Book getParticularBook(@PathVariable Integer id)
    {
        return bookdb.get(id);
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book b)
    {
        bookdb.put(b.getId(),b);
        return b;
    }


    @DeleteMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Integer id)
    {
        bookdb.remove(id);
        return "Book has been deleted";
    }

//    @GetMapping("/getdemo1/{id}/{abc}")
//    public String demoArg1(@PathVariable String id,@PathVariable String abc)
//    {
//        return "id"+id+"abc"+abc;
//    }
//
//    @GetMapping("/getdemo2/{id}/{abc}")
//    public String demoArg2(@PathVariable String id,@PathVariable String abc)
//    {
//        return "id"+id+"abc"+abc;
//    }
//
//    @GetMapping("/getdemo3/{id}/{abc}")
//    public String demoArg3(@PathVariable String id,@PathVariable String abc)
//    {
//        return "id"+id+"abc"+abc;
//    }
}


