package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.domain.dto.EntryDto;
import com.budgetingapp.cashy.domain.entities.EntryEntity;
import com.budgetingapp.cashy.mappers.Mapper;
import com.budgetingapp.cashy.services.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EntryController {

    EntryService entryService;

    private final Mapper<EntryEntity, EntryDto> entryMapper;

    public EntryController(EntryService entryService, Mapper<EntryEntity, EntryDto> entryMapper) {
        this.entryService = entryService;
        this.entryMapper = entryMapper;
    }

    @GetMapping(path = "/entries")
    public List<EntryDto> listEntries() {
        List<EntryEntity> entryEntities = entryService.findAll();
        return entryEntities.stream()
                .map(entryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/entries")
    public ResponseEntity<EntryDto> createEntry(@RequestBody EntryDto entryDto) {
        EntryEntity entryEntity = entryMapper.mapFrom(entryDto);
        EntryEntity savedEntryEntity = entryService.save(entryEntity);
        return new ResponseEntity<>(entryMapper.mapTo(savedEntryEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/entries/{id}")
    public ResponseEntity<EntryDto> getEntry(@PathVariable Long id) {
        Optional<EntryEntity> entryEntity = entryService.findOne(id);
        return entryEntity.map(entryEntity1 -> {
            EntryDto entryDto = entryMapper.mapTo(entryEntity1);
            return new ResponseEntity<>(entryDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/entries/{id}")
    public ResponseEntity<EntryDto> updateEntry(
            @PathVariable Long id,
            @RequestBody EntryDto entryDto
    ) {
        if (entryService.findOne(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntryEntity entryEntity = entryMapper.mapFrom(entryDto);
        EntryEntity updatedEntryEntity = entryService.partialUpdate(id, entryEntity);
        return new ResponseEntity<>(entryMapper.mapTo(updatedEntryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/entries/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) {
        entryService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
