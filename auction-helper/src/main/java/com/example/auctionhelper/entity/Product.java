package com.example.auctionhelper.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product")
@FieldNameConstants
public class Product extends IdentifiableEntity {

    @Column
    private String name;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = Price.Fields.product)
    private List<Price> prices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;
}
