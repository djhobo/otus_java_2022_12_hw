package ru.otus.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.*;

public class ClientServlet extends HttpServlet {
    private static final String TEMPLATE_ATTR_ALL_CLIENTS = "allClients";
    private static final String CLIENT_PAGE_TEMPLATE = "clients.html";
    private final DBServiceClient clientService;

    public ClientServlet(TemplateProcessor templateProcessor, DBServiceClient clientService) {
        this.templateProcessor = templateProcessor;
        this.clientService = clientService;
    }

    private final TemplateProcessor templateProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = clientService.findAll();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_ALL_CLIENTS, clients);
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CLIENT_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("clientName");
        String address = req.getParameter("clientAddress");
        String phone = req.getParameter("clientPhone");

        List<Phone> phones = List.of(new Phone(null, phone));
        Client client = new Client(null, name, new Address(null, address), phones);
        clientService.saveClient(client);
        resp.sendRedirect("/clients");
    }
}
