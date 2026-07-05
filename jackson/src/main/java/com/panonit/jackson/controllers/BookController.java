package com.panonit.jackson.controllers;

import com.panonit.jackson.domain.Book;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class BookController {

    @GetMapping(path = "/books")
    public List<Book> getBooks() {
        Book book = Book.builder()
                .isbn("123")
                .title("Song of ice and fire")
                .author("J.R.R. Martin")
                .yearPublished("1996")
                .build();

        return List.of(book);
    }

    @PostMapping(path = "/books")
    public Book createBook(@RequestBody Book book) {
        log.info("Book created: " + book.toString());

        return book;
    }
}
