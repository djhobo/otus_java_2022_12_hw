package ru.otus.model.dto;

import java.util.List;

public record PhoneDTO(String firstNumber, String secondNumber) {

    public List<String> getNumbers() {
        return List.of(firstNumber, secondNumber);
    }
}
