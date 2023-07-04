package ru.otus;

import ru.otus.test.SomeTest;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        TestExecutor.execute(SomeTest.class);
    }
}
