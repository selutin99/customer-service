package com.galua.onlinestore.offerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "customers")
@ToString(exclude = "customers")
@NoArgsConstructor
@Entity
@Table(name="paid_type")
public class PaidType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                },
                mappedBy = "paidType")
    private List<Customers> customers = new ArrayList<>();

    public PaidType(String name) {
        this.name = name;
    }
}
