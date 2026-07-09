package com.panonit.blogz.services.impl;

import com.panonit.blogz.domain.dtos.CategoryDto;
import com.panonit.blogz.domain.dtos.CreateCategoryRequest;
import com.panonit.blogz.domain.entities.Category;
import com.panonit.blogz.mappers.CategoryMapper;
import com.panonit.blogz.repositories.CategoryRepository;
import com.panonit.blogz.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> listCategories() {
        return repository.findAllWithPostCount().stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }

        return mapper.toDto(repository.save(mapper.toEntity(request)));
    }

    @Override
    public void deleteCategory(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Category is required");
        }

        Optional<Category> category = repository.findById(id);
        if (category.isEmpty()) {
            return;
        }

        if (!category.get().getPosts().isEmpty()) {
            throw new IllegalStateException("Category has posts associated with it");
        }

        repository.deleteById(id);
    }
}
