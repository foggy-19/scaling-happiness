package com.panonit.restless.controllers;

import com.panonit.restless.domain.dto.BookDto;
import com.panonit.restless.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PUT    /books/{isbn} // 201 CREATED (PUT was used because we are providing exact ID, otherwise it would be POST)
 * GET    /books/{isbn} // 200
 * GET    /books        // 200
 * PUT    /books{isbn}  // 200 (Update, full update all attributes)
 * PATCH  /books/{isbn} // 200 (Update, partial update only provided attributes)
 * DELETE /books/{isbn} // 204 NO-BODY
 */
@RestController
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        return service.replaceBook(isbn, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(service.createBook(isbn, book), HttpStatus.CREATED));
    }


    @GetMapping(path = "/books")
    public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(service.getAllBooks(pageable));
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("isbn") String isbn) {
        return service.getBookById(isbn)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        return service.updateBook(isbn, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("isbn") String isbn) {
        service.deleteBook(isbn);

        return ResponseEntity.noContent().build();
    }
}
