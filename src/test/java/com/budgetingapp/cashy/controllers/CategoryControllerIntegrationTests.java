package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.dto.CategoryDto;
import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import com.budgetingapp.cashy.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTests {

    private final CategoryService categoryService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public CategoryControllerIntegrationTests(CategoryService categoryService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateCategorySuccessfullyReturnsHttpStatus201AndSavedCategory() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);

        CategoryDto categoryDto = TestDataUtil.createTestCategoryDtoA();
        String categoryDtoString = objectMapper.writeValueAsString(categoryDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoString)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.subcategories").value(new ArrayList<>())
        );
    }

    @Test
    public void testThatListCategoriesReturnsHttpStatus200AndListOfCategories() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].subcategories").value(new ArrayList<>())
        );
    }

    @Test
    public void testThatGetCategoryReturnsHttpStatus200AndCategoryWhenCategoryExists() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/" + savedCategoryEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.subcategories").value(new ArrayList<>())
        );
    }

    @Test
    public void testThatGetCategoryReturnsHttpStatus404WhenCategoryDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/9999")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateCategoryReturnsHttpStatus200AndSavedCategoryWhenCategoryExists() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);
        CategoryDto categoryDto = TestDataUtil.createTestCategoryDtoA();
        categoryDto.setName("UPDATED");
        String categoryDtoJson = objectMapper.writeValueAsString(categoryDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/categories/" + savedCategoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.icon").value(savedCategoryEntity.getIcon())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.parentCategory").value(savedCategoryEntity.getParentCategory())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.subcategories").value(savedCategoryEntity.getSubcategories())
        );
    }

    @Test
    public void testThatUpdateCategoryReturnsHttpStatus404WhenCategoryDoesNotExist() throws Exception {
        CategoryDto categoryDto = TestDataUtil.createTestCategoryDtoA();
        String categoryDtoJson = objectMapper.writeValueAsString(categoryDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/categories/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteCategoryReturnsHttpStatus204WhenCategoryExists() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/categories/" + savedCategoryEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteCategoryReturnsHttpStatus204WhenCategoryDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/categories/9999")
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
