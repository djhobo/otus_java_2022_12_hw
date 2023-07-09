package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionAtEvenSecond implements Processor {
    DateTimeProvider time;

    public ProcessorThrowExceptionAtEvenSecond(DateTimeProvider time) {
        this.time = time;
    }

    @Override
    public Message process(Message message) {
        if (time.getDate().getSecond() % 2 == 0) {
            throw new RuntimeException(String.valueOf(message.getId()));
        }
        return message;
    }
}
