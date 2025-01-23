package myhaja.kata.banking.application.port.out;

import myhaja.kata.banking.application.domain.model.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
