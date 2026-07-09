package com.panonit.blogz.services;

import com.panonit.blogz.domain.dtos.CategoryDto;
import com.panonit.blogz.domain.dtos.CreateCategoryRequest;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> listCategories();

    CategoryDto createCategory(CreateCategoryRequest request);

    void deleteCategory(UUID id);
}
