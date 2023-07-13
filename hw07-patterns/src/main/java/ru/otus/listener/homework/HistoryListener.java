package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        List<String> data13 = msg.getField13().getData().stream().toList();
        ObjectForMessage obj = new ObjectForMessage();
        obj.setData(data13);

        this.map.put(msg.getId(), msg.toBuilder().field13(obj).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(this.map.get(id));
    }
}
