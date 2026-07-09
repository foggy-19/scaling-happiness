package com.panonit.blogz.controllers;

import com.panonit.blogz.domain.dtos.CategoryDto;
import com.panonit.blogz.domain.dtos.CreateCategoryRequest;
import com.panonit.blogz.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        return new ResponseEntity<>(service.listCategories(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(service.createCategory(request), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category_id") UUID id) {
        service.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
