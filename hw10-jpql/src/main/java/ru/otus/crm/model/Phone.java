package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone implements Cloneable {
    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long id;
    private String phoneNumber;

    private Long client_id;

    public Phone(Long id, String number) {
        this.id = id;
        this.phoneNumber = number;
    }

    public Phone clone() {
        return new Phone(this.id, this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + phoneNumber + '\'' +
                '}';
    }
}
