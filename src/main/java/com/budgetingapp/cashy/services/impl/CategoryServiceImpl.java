package com.budgetingapp.cashy.services.impl;

import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import com.budgetingapp.cashy.repositories.CategoryRepository;
import com.budgetingapp.cashy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return StreamSupport.stream(
                categoryRepository.findAll().spliterator(),
                false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryEntity> findOne(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public CategoryEntity partialUpdate(Long id, CategoryEntity categoryEntity) {
        categoryEntity.setId(id);
        return categoryRepository.findById(id).map(existingCategory -> {
            Optional.ofNullable(categoryEntity.getName()).ifPresent(existingCategory::setName);
            Optional.ofNullable(categoryEntity.getIcon()).ifPresent(existingCategory::setIcon);
            Optional.ofNullable(categoryEntity.getParentCategory()).ifPresent(existingCategory::setParentCategory);
            Optional.ofNullable(categoryEntity.getSubcategories()).ifPresent(existingCategory::setSubcategories);
            return existingCategory;
        }).orElseThrow(() -> new RuntimeException("Category does not exist"));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
