package com.myimdb.searchbook.controller;


import com.myimdb.searchbook.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookController {

    Map<Integer, Book> bookdb = new HashMap<>();

    public BookController() {
        bookdb.put(1,new Book(1,"Crack Interview atFAANG","Richa Tayal",100.0));
        bookdb.put(2,new Book(2,"Head First with Java","Mohan",200.0));
    }

    @GetMapping("/getAllBooks")
    public Collection<Book> getAllBooks(){
        return bookdb.values();
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
}
