package com.example.auctionhelper.mapper;

import com.example.auctionhelper.dto.PriceDto;
import com.example.auctionhelper.entity.Price;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceMapper {


    public List<PriceDto> map(List<Price> prices) {
        if (prices == null) {
            return List.of();
        }
        List<PriceDto> result = new ArrayList<>();
        for (Price price : prices) {
            PriceDto dto = map(price);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

    public PriceDto map(Price price) {
        if (price == null) {
            return null;
        }
        PriceDto dto = new PriceDto();
        //todo...
        return dto;
    }
}
