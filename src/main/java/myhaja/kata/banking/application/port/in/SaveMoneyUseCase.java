package myhaja.kata.banking.application.port.in;

public interface SaveMoneyUseCase {
    boolean saveMoney(SaveMoneyCommand saveMoneyCommand);
}
