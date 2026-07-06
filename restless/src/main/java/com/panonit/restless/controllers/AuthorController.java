package com.panonit.restless.controllers;

import com.panonit.restless.domain.dto.AuthorDto;
import com.panonit.restless.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * POST   /authors      // 201 CREATED
 * GET    /authors/{id} // 200
 * GET    /authors      // 200
 * PUT    /authors/{id} // 200 (Update, full update all attributes)
 * PATCH  /authors/{id} // 200 (Update, partial update only provided attributes)
 * DELETE /authors/{id} // 204 NO-BODY
 */
@RestController
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        return new ResponseEntity<>(service.createAuthor(author), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(service.getAllAuthors());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return service.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> replaceAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author) {
        return service.replaceAuthor(id, author)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author) {
        return service.updateAuthor(id, author)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable("id") Long id) {
        service.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }
}
