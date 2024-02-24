package com.budgetingapp.cashy.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String name;

    private String icon;

    private String parentCategory;

    private List<String> subcategories;
}
