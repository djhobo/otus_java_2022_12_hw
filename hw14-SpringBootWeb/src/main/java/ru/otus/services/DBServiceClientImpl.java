package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DBServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DBServiceClientImpl.class);
    private final ClientRepository repository;

    public DBServiceClientImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client saveClient(Client client) {
        var savedClient = repository.save(client);
        log.info("saved client: {}", savedClient);
        return savedClient;
    }

    @Override
    public List<Client> getClients() {
        List<Client> clientList = new ArrayList<>();
        repository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }

}
