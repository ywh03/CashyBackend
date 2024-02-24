package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryRepositoryIntegrationTests {

    private CategoryRepository underTest;

    @Autowired
    public CategoryRepositoryIntegrationTests(CategoryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    void testThatCategoryCanBeCreatedAndRecalled() {
        CategoryEntity categoryEntityA = TestDataUtil.createTestCategoryEntityA();
        underTest.save(categoryEntityA);
        Optional<CategoryEntity> result = underTest.findById(categoryEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(categoryEntityA);
    }

    @Test
    void testThatMultipleCategoriesCanBeCreatedAndRecalled() {
        CategoryEntity categoryEntityA = TestDataUtil.createTestCategoryEntityA();
        underTest.save(categoryEntityA);
        CategoryEntity categoryEntityB = TestDataUtil.createTestCategoryEntityB();
        underTest.save(categoryEntityB);
        Iterable<CategoryEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(categoryEntityA, categoryEntityB);
    }

    @Test
    void testThatCategoryCanBeUpdated() {
        CategoryEntity categoryEntityA = TestDataUtil.createTestCategoryEntityA();
        underTest.save(categoryEntityA);
        categoryEntityA.setName("UPDATED");
        underTest.save(categoryEntityA);
        Optional<CategoryEntity> result = underTest.findById(categoryEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(categoryEntityA);
    }

    @Test
    void testThatCategoryCanBeDeleted() {
        CategoryEntity categoryEntityA = TestDataUtil.createTestCategoryEntityA();
        underTest.save(categoryEntityA);
        underTest.deleteById(categoryEntityA.getId());
        Optional<CategoryEntity> result = underTest.findById(categoryEntityA.getId());
        assertThat(result).isEmpty();
    }
}
