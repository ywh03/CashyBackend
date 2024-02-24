package com.budgetingapp.cashy.repositories;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.entities.EntryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EntryRepositoryIntegrationTests {

    private final EntryRepository underTest;

    @Autowired
    public EntryRepositoryIntegrationTests(EntryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    void testThatEntryCanBeCreatedAndRecalled() {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        underTest.save(entryEntity);
        Optional<EntryEntity> result = underTest.findById(entryEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(entryEntity);
    }

    @Test
    void testThatMultipleEntriesCanBeCreatedAndRecalled() {
        EntryEntity entryEntityA = TestDataUtil.createTestEntryEntityA();
        underTest.save(entryEntityA);
        EntryEntity entryEntityB = TestDataUtil.createTestEntryEntityB();
        underTest.save(entryEntityB);
        Iterable<EntryEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(entryEntityA, entryEntityB);
    }

    @Test
    void testThatEntryCanBeUpdated() {
        EntryEntity entryEntityA = TestDataUtil.createTestEntryEntityA();
        underTest.save(entryEntityA);
        entryEntityA.setNote("UPDATED");
        underTest.save(entryEntityA);
        Optional<EntryEntity> result = underTest.findById(entryEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(entryEntityA);
    }

    @Test
    void testThatEntryCanBeDeleted() {
        EntryEntity entryEntityA = TestDataUtil.createTestEntryEntityA();
        underTest.save(entryEntityA);
        underTest.deleteById(entryEntityA.getId());
        Optional<EntryEntity> result = underTest.findById(entryEntityA.getId());
        assertThat(result).isEmpty();
    }
}
