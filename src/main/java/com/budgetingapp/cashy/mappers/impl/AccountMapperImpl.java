package com.budgetingapp.cashy.mappers.impl;

import com.budgetingapp.cashy.domain.dto.AccountDto;
import com.budgetingapp.cashy.domain.entities.AccountEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements Mapper<AccountEntity, AccountDto> {

    private final ModelMapper modelmapper;

    public AccountMapperImpl(ModelMapper modelmapper) {
        this.modelmapper = modelmapper;
    }

    @Override
    public AccountDto mapTo(AccountEntity accountEntity) {
        return modelmapper.map(accountEntity, AccountDto.class);
    }

    @Override
    public AccountEntity mapFrom(AccountDto accountDto) {
        return modelmapper.map(accountDto, AccountEntity.class);
    }
}
