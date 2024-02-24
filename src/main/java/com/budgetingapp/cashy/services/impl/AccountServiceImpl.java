package com.budgetingapp.cashy.services.impl;

import com.budgetingapp.cashy.domain.entities.AccountEntity;
import com.budgetingapp.cashy.repositories.AccountRepository;
import com.budgetingapp.cashy.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity save(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    public List<AccountEntity> findAll() {
        return StreamSupport.stream(
                accountRepository.findAll().spliterator(),
                false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<AccountEntity> findOne(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountEntity partialUpdate(Long id, AccountEntity accountEntity) {
        accountEntity.setId(id);
        return accountRepository.findById(id).map(existingAccount -> {
            Optional.ofNullable(accountEntity.getName()).ifPresent(existingAccount::setName);
            Optional.ofNullable(accountEntity.getBalance()).ifPresent(existingAccount::setBalance);
            Optional.ofNullable(accountEntity.getHexColor()).ifPresent(existingAccount::setHexColor);
            Optional.ofNullable(accountEntity.getCurrency()).ifPresent(existingAccount::setCurrency);
            return existingAccount;
        }).orElseThrow(() -> new RuntimeException("Account does not exist"));
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
