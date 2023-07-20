package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.model.dto.ClientDTO;
import ru.otus.model.dto.PhoneDTO;
import ru.otus.services.DBServiceClient;

import java.util.List;

@Controller
public class AppControllers {
    private final DBServiceClient repo;

    public AppControllers(DBServiceClient repo) {
        this.repo = repo;
    }

    @GetMapping({"/"})
    public String indexView() {
        return "index";
    }

    @GetMapping({"/clients"})
    public String clientsListView(Model model) {
        List<Client> clients = repo.getClients();
        model.addAttribute("clientsList", clients);
        ClientDTO clientDTO = new ClientDTO("test", new Address("test"));
        PhoneDTO phoneDTO = new PhoneDTO("test", "test");
        model.addAttribute("phones", phoneDTO);
        model.addAttribute("client", clientDTO);
        return "clients";
    }

    @PostMapping({"/client/save"})
    public RedirectView clientSave(@ModelAttribute ClientDTO client, @ModelAttribute PhoneDTO phones) {
        Client clientSaved = new Client(client.name(), client.address(),
                List.of(new Phone(phones.firstNumber()), new Phone(phones.firstNumber())));
        System.out.println(client);
        repo.saveClient(clientSaved);
        return new RedirectView("/clients", true);
    }
}
