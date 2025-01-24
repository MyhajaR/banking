package myhaja.kata.banking.application.port.out;

import myhaja.kata.banking.domain.model.Account;


public interface LoadAccountPort {

    Account loadAccount(Long accountId);
}
