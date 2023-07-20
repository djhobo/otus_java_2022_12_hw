package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone")
public class Phone {
    @Id
    @Column("id")
    private final Long id;

    private final String phoneNumber;

    @Column("client_id")
    private final Long clientId;

    public Phone(String phoneNumber) {
        this.id = null;
        this.clientId = null;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", number='" + phoneNumber + '\'' +
                '}';
    }
}
