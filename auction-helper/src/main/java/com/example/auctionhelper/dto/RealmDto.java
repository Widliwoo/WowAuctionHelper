package com.example.auctionhelper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RealmDto {
    private String name;
    private Integer multiplier;
    private List<ProductDto> products;
}
