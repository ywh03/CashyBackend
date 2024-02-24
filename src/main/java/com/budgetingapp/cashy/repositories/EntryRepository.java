package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.domain.entities.EntryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends CrudRepository<EntryEntity, Long> {

}
