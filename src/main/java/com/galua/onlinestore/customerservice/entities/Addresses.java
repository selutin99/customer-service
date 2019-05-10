package com.galua.onlinestore.customerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "customers")
@ToString(exclude = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="addresses")
public class Addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String city;

    private String state;

    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Customers> customers;

    public Addresses(String city, String state, String country, Customers... offers) {
        this.city = city;
        this.state = state;
        this.country = country;

        this.customers = Stream.of(offers).collect(Collectors.toList());
        this.customers.forEach(x -> x.setAddress(this));
    }
}
