package myhaja.kata.banking.adapter.in.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import myhaja.kata.banking.application.port.in.Transaction;
import myhaja.kata.banking.domain.model.Activity;
import myhaja.kata.banking.domain.service.AccountServicePort;
import myhaja.kata.banking.application.port.in.WithDrawRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServicePort accountService;

    @PostMapping(path = "/money/save")
    public ResponseEntity<Map<String, String>> saveMoney(@Valid @RequestBody Transaction transaction) {
        accountService.saveMoney(transaction);
        Map<String, String> response = new HashMap<>();
        response.put("message", transaction.money().getAmount() + " successfully transferred from account ID: "
                + transaction.sourceAccountId() + " to account ID: " + transaction.targetAccountId());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/withdraw")
    public ResponseEntity<Map<String, String>> withdraw(@Valid @RequestBody WithDrawRequest request) {
        accountService.withdraw(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", request.amount() + " successfully withdrawn from account ID : " + request.accountId());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/statement/{accountId}")
    public ResponseEntity<List<Activity>> getStatements(@NotNull @PathVariable("accountId") Long accountId) {
        var activities = accountService.getStatement(accountId);
        return ResponseEntity.ok(activities);
    }
}
