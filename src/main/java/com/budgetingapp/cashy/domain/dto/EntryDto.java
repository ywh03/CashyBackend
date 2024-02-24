package com.budgetingapp.cashy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryDto {

    private Long id;

    private double amount;

    private AccountDto account;

    private LocalDateTime dateRecorded;

    private LocalDateTime dateUsed;

    private CategoryDto category;

    private String note;
}
