package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "client")
public class Client{
    @Id
    @Column("id")
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "sort_id")
    private final List<Phone> numbers;

    public Client(String name, Address address, List<Phone> numbers) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.numbers = numbers;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", numbers=" + numbers +
                '}';
    }
}
