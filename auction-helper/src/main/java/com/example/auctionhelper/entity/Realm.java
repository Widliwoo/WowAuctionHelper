package com.example.auctionhelper.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "realm")
@FieldNameConstants
public class Realm extends IdentifiableEntity {

    @Column
    private String fullName;

    @Column
    @Enumerated(EnumType.STRING)
    private Fraction fraction;

    @Column
    private Integer multiplier;

    @OneToMany(mappedBy = Product.Fields.realm)
    private List<Product> products;
}
