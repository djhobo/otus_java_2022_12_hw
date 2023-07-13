package ru.otus.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorThrowExceptionAtEvenSecond;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorThrowExceptionAtEvenSecondTest {

    private Message message;

    @BeforeEach
    public void init() {
        message = new Message.Builder(1).field1("field_1").field10("field_10").field5("field_5").build();
    }

    @Test
    @DisplayName("ProcessorThrowExceptionAtEvenSecond: throws an exception at even second")
    public void throwingExceptionProcessorTest() {
        Processor throwException = new ProcessorThrowExceptionAtEvenSecond(() -> LocalDateTime.of(2023, Month.JULY, 1, 10, 0, 2));

        try {
            throwException.process(message);
            fail();
        } catch (RuntimeException re) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("ProcessorThrowExceptionAtEvenSecond: doesn't throw exception at odd second")
    public void nonThrowingExceptionProcessorTest() {
        Processor notTrowException = new ProcessorThrowExceptionAtEvenSecond(() -> LocalDateTime.of(2023, Month.JULY, 1, 10, 0, 3));

        try {
            assertEquals(notTrowException.process(message), message);
        } catch (RuntimeException re) {
            fail();
        }
    }
}
