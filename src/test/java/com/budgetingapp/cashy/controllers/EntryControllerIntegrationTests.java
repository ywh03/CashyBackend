package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.dto.EntryDto;
import com.budgetingapp.cashy.domain.entities.EntryEntity;
import com.budgetingapp.cashy.services.EntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class EntryControllerIntegrationTests {

    private final EntryService entryService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public EntryControllerIntegrationTests(EntryService entryService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.entryService = entryService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateEntryReturnsHttpStatus201AndCreatedEntry() throws Exception {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        EntryEntity savedEntryEntity = entryService.save(entryEntity);

        EntryDto entryDto = TestDataUtil.createTestEntryDtoA();
        String entryDtoJson = objectMapper.writeValueAsString(entryDto);

        LocalDateTime testDateTime = TestDataUtil.createTestDateA();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(entryDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.amount").value(1.02)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.balance").value(0.0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.currency").value("SGD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateRecorded").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateUsed").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.subcategories").value(new ArrayList<>())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Test Note A")
        );
    }

    @Test
    public void testThatListEntriesReturnsHttpStatus200AndListOfEntries() throws Exception {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        EntryEntity savedEntryEntity = entryService.save(entryEntity);

        LocalDateTime testDateTime = TestDataUtil.createTestDateA();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/entries")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].amount").value(1.02)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].account.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].account.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].account.balance").value(0.0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].account.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].account.currency").value("SGD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].dateRecorded").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].dateUsed").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].category.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].category.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].category.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].category.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].category.subcategories").value(new ArrayList<>())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].note").value("Test Note A")
        );
    }

    @Test
    public void testThatGetEntryReturnsHttpStatus200AndEntryWhenEntryExists() throws Exception {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        EntryEntity savedEntryEntity = entryService.save(entryEntity);

        LocalDateTime testDateTime = TestDataUtil.createTestDateA();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/entries/" + savedEntryEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.amount").value(1.02)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.balance").value(0.0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.currency").value("SGD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateRecorded").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateUsed").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.subcategories").value(new ArrayList<>())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Test Note A")
        );
    }

    @Test
    public void testThatUpdateEntryReturnsHttpStatus200AndUpdatedEntry() throws Exception {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        EntryEntity savedEntryEntity = entryService.save(entryEntity);

        EntryDto entryDto = TestDataUtil.createTestEntryDtoA();
        entryDto.setNote("UPDATED");
        String entryDtoJson = objectMapper.writeValueAsString(entryDto);

        LocalDateTime testDateTime = TestDataUtil.createTestDateA();

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/entries/" + savedEntryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(entryDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.amount").value(1.02)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.balance").value(0.0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.account.currency").value("SGD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateRecorded").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateUsed").value(testDateTime.toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.name").value("Test Category A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.icon").value("test_icon.png")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.parentCategory").value("")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.category.subcategories").value(new ArrayList<>())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteEntryReturnsHttpStatus204WhenEntryExists() throws Exception {
        EntryEntity entryEntity = TestDataUtil.createTestEntryEntityA();
        EntryEntity savedEntryEntity = entryService.save(entryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/entries/" + savedEntryEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteEntryReturnsHttpStatus204WhenEntryDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/entries/9999")
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
