package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.domain.dto.CategoryDto;
import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import com.budgetingapp.cashy.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    CategoryService categoryService;

    private final Mapper<CategoryEntity, CategoryDto> categoryMapper;

    public CategoryController(CategoryService categoryService, Mapper<CategoryEntity, CategoryDto> categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping(path = "/categories")
    public List<CategoryDto> listCategories() {
        List<CategoryEntity> categories = categoryService.findAll();
        return categories.stream()
                .map(categoryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryMapper.mapFrom(categoryDto);
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);
        return new ResponseEntity<>(categoryMapper.mapTo(savedCategoryEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/categories/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntity = categoryService.findOne(id);
        return (categoryEntity.map(categoryEntity1 -> {
            CategoryDto categoryDto = categoryMapper.mapTo(categoryEntity1);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        })).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto
    ) {
        if (categoryService.findOne(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CategoryEntity categoryEntity = categoryMapper.mapFrom(categoryDto);
        CategoryEntity updatedCategoryEntity = categoryService.partialUpdate(id, categoryEntity);
        return new ResponseEntity<>(categoryMapper.mapTo(updatedCategoryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/categories/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
