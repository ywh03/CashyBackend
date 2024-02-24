package com.budgetingapp.cashy.services;

import com.budgetingapp.cashy.domain.entities.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    AccountEntity save(AccountEntity accountEntity);

    List<AccountEntity> findAll();

    Optional<AccountEntity> findOne(Long id);

    AccountEntity partialUpdate(Long id, AccountEntity accountEntity);

    void delete(Long id);
}
