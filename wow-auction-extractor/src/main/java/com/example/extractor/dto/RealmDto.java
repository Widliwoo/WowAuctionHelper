package com.example.extractor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RealmDto {
    private String name;
    private List<ProductDto> products = new ArrayList<>();
}
