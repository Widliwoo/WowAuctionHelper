package com.example.auctionhelper.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price")
@Getter
@Setter
@FieldNameConstants
public class Price extends IdentifiableEntity {

    @Column
    private Long amount;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
