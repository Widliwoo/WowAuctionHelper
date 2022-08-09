package com.example.extractor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RealmDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private List<ProductDto> products = new ArrayList<>();
}
