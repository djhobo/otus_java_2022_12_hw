package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address")
public class Address{
    @Id
    @Column("client_id")
    private final Long id;

    private final String street;

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
