package ru.otus.services;


import ru.otus.model.Client;

import java.util.List;

public interface DBServiceClient {

    Client saveClient(Client client);

    List<Client> getClients();
}
