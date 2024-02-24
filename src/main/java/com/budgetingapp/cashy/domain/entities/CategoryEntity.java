package com.budgetingapp.cashy.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    private Long id;

    private String name;

    private String icon;

    private String parentCategory;

    private List<String> subcategories;

}
