package com.budgetingapp.cashy.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    private Long id;

    private String name;

    private double balance;

    private String hexColor;

    private String currency;

}
