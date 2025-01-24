package myhaja.kata.banking.domain.service;

import myhaja.kata.banking.application.port.in.Transaction;
import myhaja.kata.banking.application.port.in.WithDrawRequest;
import myhaja.kata.banking.domain.model.Activity;

import java.util.List;

public interface AccountServicePort {

    boolean saveMoney(Transaction transaction);

    void withdraw(WithDrawRequest request);

    List<Activity> getStatement(Long accountId);
}
