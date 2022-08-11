package com.example.extractor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Price extends IdentifiableEntity {

    /**
     * Цена предмета, выраженная в медных монетах
     */
    private Long productPrice;
    private String productName;
    private String realmName;
}
