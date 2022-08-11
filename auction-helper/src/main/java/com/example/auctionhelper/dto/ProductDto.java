package com.example.auctionhelper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private String name;
    private List<PriceDto> prices;
}
