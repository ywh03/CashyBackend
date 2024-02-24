package com.budgetingapp.cashy.services;

import com.budgetingapp.cashy.domain.entities.EntryEntity;

import java.util.List;
import java.util.Optional;

public interface EntryService {
    EntryEntity save(EntryEntity entryEntity);

    List<EntryEntity> findAll();

    Optional<EntryEntity> findOne(Long id);

    EntryEntity partialUpdate(Long id, EntryEntity entryEntity);

    void delete(Long id);

}
