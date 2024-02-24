package com.budgetingapp.cashy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    private String name;

    private double balance;

    private String hexColor;

    private String currency;
}
