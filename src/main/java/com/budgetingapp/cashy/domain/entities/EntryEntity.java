package com.budgetingapp.cashy.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "entries")
public class EntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entry_id_seq")
    private Long id;

    private double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id")
    private AccountEntity account;

    private LocalDateTime dateRecorded;

    private LocalDateTime dateUsed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String note;

}
