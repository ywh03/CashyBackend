package com.budgetingapp.cashy.mappers.impl;

import com.budgetingapp.cashy.domain.dto.CategoryDto;
import com.budgetingapp.cashy.domain.entities.CategoryEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto> {

    private final ModelMapper modelMapper;

    public CategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto mapTo(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }
}
