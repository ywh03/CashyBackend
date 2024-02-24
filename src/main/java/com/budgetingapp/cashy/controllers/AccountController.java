package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.domain.dto.AccountDto;
import com.budgetingapp.cashy.domain.entities.AccountEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import com.budgetingapp.cashy.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    private final AccountService accountService;

    private final Mapper<AccountEntity, AccountDto> accountMapper;

    public AccountController(AccountService accountService, Mapper<AccountEntity, AccountDto> authorMapper) {
        this.accountService = accountService;
        this.accountMapper = authorMapper;
    }

    @GetMapping(path = "/accounts")
    public List<AccountDto> listAccounts() {
        List<AccountEntity> accounts = accountService.findAll();
        return accounts.stream()
                .map(accountMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity savedAccountEntity = accountService.save(accountEntity);
        return new ResponseEntity<>(accountMapper.mapTo(accountEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {
        Optional<AccountEntity> accountEntity = accountService.findOne(id);
        return (accountEntity.map(accountEntity1 -> {
            AccountDto accountDto = accountMapper.mapTo(accountEntity1);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        })).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(
            @PathVariable("id") Long id,
            @RequestBody AccountDto accountDto) {
        if (accountService.findOne(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountEntity accountEntity = accountMapper.mapFrom(accountDto);
        AccountEntity updatedAccountEntity = accountService.partialUpdate(id, accountEntity);
        return new ResponseEntity<>(accountMapper.mapTo(updatedAccountEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/accounts/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
