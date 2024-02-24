package com.budgetingapp.cashy.services.impl;

import com.budgetingapp.cashy.domain.entities.EntryEntity;
import com.budgetingapp.cashy.repositories.EntryRepository;
import com.budgetingapp.cashy.services.EntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;

    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public EntryEntity save(EntryEntity entryEntity) {
        return entryRepository.save(entryEntity);
    }

    @Override
    public List<EntryEntity> findAll() {
        return StreamSupport.stream(entryRepository
                    .findAll()
                    .spliterator(),
                    false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EntryEntity> findOne(Long id) {
        return entryRepository.findById(id);
    }

    @Override
    public EntryEntity partialUpdate(Long id, EntryEntity entryEntity) {
        entryEntity.setId(id);
        return entryRepository.findById(id).map(existingEntry -> {
            Optional.ofNullable(entryEntity.getAmount()).ifPresent(existingEntry::setAmount);
            Optional.ofNullable(entryEntity.getAccount()).ifPresent(existingEntry::setAccount);
            Optional.ofNullable(entryEntity.getDateUsed()).ifPresent(existingEntry::setDateUsed);
            Optional.ofNullable((entryEntity.getCategory())).ifPresent(existingEntry::setCategory);
            Optional.ofNullable(entryEntity.getNote()).ifPresent(existingEntry::setNote);
            return entryRepository.save(existingEntry);
        }).orElseThrow(() -> new RuntimeException("Entry does not exist"));
    }

    @Override
    public void delete(Long id) {
        entryRepository.deleteById(id);
    }

}
