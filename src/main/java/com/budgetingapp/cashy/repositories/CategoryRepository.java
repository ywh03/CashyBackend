package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
}
