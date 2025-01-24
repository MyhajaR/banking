package myhaja.kata.banking.adapter.in.api;

import myhaja.kata.banking.application.port.in.Transaction;
import myhaja.kata.banking.application.port.in.WithDrawRequest;
import myhaja.kata.banking.domain.exception.DepositException;
import myhaja.kata.banking.domain.model.Activity;
import myhaja.kata.banking.domain.model.Money;
import myhaja.kata.banking.domain.service.AccountServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServicePort accountService;

    @Test
    void givenValidTransaction_whenSaveMoney_thenReturn200() throws Exception {
        Transaction transaction = new Transaction(
                1L,                     // sourceAccountId
                2L,                                   // targetAccountId
                Money.of(BigDecimal.valueOf(100))     // amount
        );

        when(accountService.saveMoney(transaction)).thenReturn(true);

        mockMvc.perform(post("/accounts/money/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\": 1, \"targetAccountId\": 2, \"money\": {\"amount\": 100}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("100 successfully transferred from account ID: 1 to account ID: 2"));

        verify(accountService).saveMoney(transaction);

    }

    @Test
    void givenInValidRequest_whenSaveMoney_thenReturnBadRequest() throws Exception {

        when(accountService.saveMoney(any(Transaction.class))).thenReturn(true);

        mockMvc.perform(post("/accounts/money/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        // Case sourceAccountId is null
                        .content("{\"targetAccountId\": 2, \"money\": {\"amount\": 100}}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDepositExceptionThrown_thenReturnBadRequestWithErrorMessage() throws Exception {

        when(accountService.saveMoney(any(Transaction.class))).thenThrow(new DepositException("test"));

        mockMvc.perform(post("/accounts/money/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\": 1, \"targetAccountId\": 2, \"money\": {\"amount\": 100}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("test"));

    }

    @Test
    void givenValidRequest_whenWithdraw_thenReturn200() throws Exception {

        doNothing().when(accountService).withdraw(any(WithDrawRequest.class));

        mockMvc.perform(post("/accounts/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\": 1, \"amount\": 100}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("100 successfully withdrawn from account ID : 1"));
    }


    @Test
    void givenValidAccount_shouldReturnStatement() throws Exception {
        // Given
        BigDecimal expectedAmount = BigDecimal.valueOf(100L);
        Long expectedAccount = 25L;

        var activities = List.of(
                new Activity(1L, expectedAccount, 25L, 26L, LocalDate.of(2025,1, 19).atStartOfDay(), Money.of(expectedAmount))
        );

        // When & then
        when(accountService.getStatement(expectedAccount)).thenReturn(activities);

        mockMvc.perform(get("/accounts/statement/{accountId}", expectedAccount)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].money.amount").value(expectedAmount));
    }
}