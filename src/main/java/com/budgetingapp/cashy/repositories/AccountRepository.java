package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.domain.entities.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
}
