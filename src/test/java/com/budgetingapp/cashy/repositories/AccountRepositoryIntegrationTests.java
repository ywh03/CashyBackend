package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.entities.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountRepositoryIntegrationTests {

    private final AccountRepository underTest;

    @Autowired
    public AccountRepositoryIntegrationTests(AccountRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    void testThatAccountCanBeCreatedAndRecalled() {
        AccountEntity accountEntityA = TestDataUtil.createTestAccountEntityA();
        underTest.save(accountEntityA);
        Optional<AccountEntity> result = underTest.findById(accountEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(accountEntityA);
    }

    @Test
    void testThatMultipleAccountsCanBeCreatedAndRecalled() {
        AccountEntity accountEntityA = TestDataUtil.createTestAccountEntityA();
        underTest.save(accountEntityA);
        AccountEntity accountEntityB = TestDataUtil.createTestAccountEntityB();
        underTest.save(accountEntityB);
        Iterable<AccountEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(accountEntityA, accountEntityB);
    }

    @Test
    void testThatAccountCanBeUpdated() {
        AccountEntity accountEntityA = TestDataUtil.createTestAccountEntityA();
        underTest.save(accountEntityA);
        accountEntityA.setName("UPDATED");
        underTest.save(accountEntityA);
        Optional<AccountEntity> result = underTest.findById(accountEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(accountEntityA);
    }

    @Test
    void testThatAccountCanBeDeleted() {
        AccountEntity accountEntityA = TestDataUtil.createTestAccountEntityA();
        underTest.save(accountEntityA);
        underTest.deleteById(accountEntityA.getId());
        Optional<AccountEntity> result = underTest.findById(accountEntityA.getId());
        assertThat(result).isEmpty();
    }
}
