package com.galua.onlinestore.offerservice.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "types")
@ToString(exclude = "types")
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Addresses address;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                })
    @JoinTable(name = "customers_paid_types",
               joinColumns = @JoinColumn(name = "paid_type_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    private List<PaidType> types = new ArrayList<>();

    public Customers(int id, String firstName, String lastName, String email, String password, String phoneNumber, PaidType... types) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;

        this.types = Stream.of(types).collect(Collectors.toList());
        this.types.forEach(x -> x.getCustomers().add(this));
    }
}