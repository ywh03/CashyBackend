package com.budgetingapp.cashy.controllers;

import com.budgetingapp.cashy.TestDataUtil;
import com.budgetingapp.cashy.domain.dto.AccountDto;
import com.budgetingapp.cashy.domain.entities.AccountEntity;
import com.budgetingapp.cashy.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AccountControllerIntegrationTests {

    private final AccountService accountService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AccountControllerIntegrationTests(AccountService accountService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateAccountSuccessfullyReturnsHttpStatus201AndSavedAccount() throws Exception {
        AccountDto testAccountDto = TestDataUtil.createTestAccountDtoA();
        testAccountDto.setId(null);
        String accountJson = objectMapper.writeValueAsString(testAccountDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.balance").value(0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.currency").value("SGD")
        );
    }

    @Test
    public void testThatListAccountsReturnsHttpStatus200AndListOfAccounts() throws Exception {
        AccountEntity testAccountEntity = TestDataUtil.createTestAccountEntityA();
        testAccountEntity.setId(null);
        accountService.save(testAccountEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].balance").value(0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].currency").value("SGD")
        );
    }

    @Test
    public void testThatGetAccountReturnsHttpStatus200AndAccountWhenAccountExists() throws Exception {
        AccountEntity testAccountEntity = TestDataUtil.createTestAccountEntityA();
        accountService.save(testAccountEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test Account A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.balance").value(0)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hexColor").value("#fff")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.currency").value("SGD")
        );
    }

    @Test
    public void testThatGetAccountReturnsHttpStatus404WhenAccountDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateAccountReturnsHttpStatus200AndUpdatedAccountWhenAccountExists() throws Exception {
        AccountEntity accountEntity = TestDataUtil.createTestAccountEntityA();
        AccountEntity savedAccountEntity = accountService.save(accountEntity);

        AccountDto accountDto = TestDataUtil.createTestAccountDtoA();
        accountDto.setName("UPDATED");
        String accountDtoJson = objectMapper.writeValueAsString(accountDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/accounts/" + savedAccountEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAccountEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.balance").value(savedAccountEntity.getBalance())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hexColor").value(savedAccountEntity.getHexColor())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.currency").value(savedAccountEntity.getCurrency())
        );
    }

    @Test
    public void testThatUpdateAccountReturnsHttpStatus404WhenAccountDoesNotExist() throws Exception {
        AccountDto accountDto = TestDataUtil.createTestAccountDtoA();
        String accountDtoJson = objectMapper.writeValueAsString(accountDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/accounts/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteAccountReturnsHttpStatus204WhenAccountExists() throws Exception {
        AccountEntity accountEntity = TestDataUtil.createTestAccountEntityA();
        accountService.save(accountEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/" + accountEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAccountReturnsHttpStatus204WhenAccountDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/9999")
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
