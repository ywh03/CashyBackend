package com.budgetingapp.cashy;

import com.budgetingapp.cashy.domain.dto.AccountDto;
import com.budgetingapp.cashy.domain.dto.CategoryDto;
import com.budgetingapp.cashy.domain.dto.EntryDto;
import com.budgetingapp.cashy.domain.entities.AccountEntity;
import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import com.budgetingapp.cashy.domain.entities.EntryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class TestDataUtil {

    public static LocalDateTime createTestDateA() {
        return LocalDateTime.of(2023, Month.OCTOBER, 15, 14, 30, 15);
    }

    public static AccountEntity createTestAccountEntityA() {
        return AccountEntity.builder()
                .id(1L)
                .name("Test Account A")
                .balance(0)
                .hexColor("#fff")
                .currency("SGD")
                .build();
    }

    public static AccountEntity createTestAccountEntityB() {
        return AccountEntity.builder()
                .id(2L)
                .name("Test Account B")
                .balance(0)
                .hexColor("#000")
                .currency("SGD")
                .build();
    }

    public static AccountDto createTestAccountDtoA() {
        return AccountDto.builder()
                .id(1L)
                .name("Test Account A")
                .balance(0)
                .hexColor("#fff")
                .currency("SGD")
                .build();
    }

    public static CategoryEntity createTestCategoryEntityA() {
        List<String> subcategories = new ArrayList<>();
        return CategoryEntity.builder()
                .id(1L)
                .name("Test Category A")
                .icon("test_icon.png")
                .parentCategory("")
                .subcategories(subcategories)
                .build();
    }

    public static CategoryEntity createTestCategoryEntityB() {
        List<String> subcategories = new ArrayList<>();
        return CategoryEntity.builder()
                .id(2L)
                .name("Test Category B")
                .icon("test_icon.png")
                .parentCategory("")
                .subcategories(subcategories)
                .build();
    }

    public static CategoryDto createTestCategoryDtoA() {
        List<String> subcategories = new ArrayList<>();
        return CategoryDto.builder()
                .id(1L)
                .name("Test Category A")
                .icon("test_icon.png")
                .parentCategory("")
                .subcategories(subcategories)
                .build();
    }

    public static EntryEntity createTestEntryEntityA() {
        LocalDateTime testDate = createTestDateA();
        AccountEntity testAccount = createTestAccountEntityA();
        CategoryEntity testCategory = createTestCategoryEntityA();
        return EntryEntity.builder()
                .id(1L)
                .amount(1.02)
                .account(testAccount) //generate account
                .dateRecorded(testDate) //generate date
                .dateUsed(testDate)
                .category(testCategory)
                .note("Test Note A")
                .build();
    }

    public static EntryEntity createTestEntryEntityB() {
        LocalDateTime testDate = createTestDateA();
        AccountEntity testAccount = createTestAccountEntityA();
        CategoryEntity testCategory = createTestCategoryEntityA();
        return EntryEntity.builder()
                .id(2L)
                .amount(1.03)
                .account(testAccount) //generate account
                .dateRecorded(testDate) //generate date
                .dateUsed(testDate)
                .category(testCategory)
                .note("Test Note B")
                .build();
    }

    public static EntryDto createTestEntryDtoA() {
        LocalDateTime testDate = createTestDateA();
        AccountDto testAccount = createTestAccountDtoA();
        CategoryDto testCategory = createTestCategoryDtoA();
        return EntryDto.builder()
                .id(1L)
                .amount(1.02)
                .account(testAccount) //generate account
                .dateRecorded(testDate) //generate date
                .dateUsed(testDate)
                .category(testCategory)
                .note("Test Note A")
                .build();
    }
}
