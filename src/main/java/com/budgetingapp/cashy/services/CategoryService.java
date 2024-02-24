package com.budgetingapp.cashy.services;

import com.budgetingapp.cashy.domain.entities.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryEntity save(CategoryEntity categoryEntity);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findOne(Long id);

    CategoryEntity partialUpdate(Long id, CategoryEntity categoryEntity);

    void delete(Long id);
}
