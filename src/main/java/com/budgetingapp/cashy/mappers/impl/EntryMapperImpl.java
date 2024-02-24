package com.budgetingapp.cashy.mappers.impl;

import com.budgetingapp.cashy.domain.dto.EntryDto;
import com.budgetingapp.cashy.domain.entities.EntryEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntryMapperImpl implements Mapper<EntryEntity, EntryDto> {

    private final ModelMapper modelMapper;

    public EntryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EntryDto mapTo(EntryEntity entryEntity) {
        return modelMapper.map(entryEntity, EntryDto.class);
    }

    @Override
    public EntryEntity mapFrom(EntryDto entryDto) {
        return modelMapper.map(entryDto, EntryEntity.class);
    }
}
