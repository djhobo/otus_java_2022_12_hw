package ru.otus.atm.mechanism.cell;

import ru.otus.atm.mechanism.cell.exception.CellOperationException;
import ru.otus.atm.money.Banknote;

public interface Cell<T extends Banknote> {
    T extract() throws CellOperationException;

    void insertBanknote(T banknote) throws CellOperationException;

    int getBanknotesAmount();

    Integer getDenomination();
}