package com.example.auctionhelper.mapper;

import com.example.auctionhelper.dto.ProductDto;
import com.example.auctionhelper.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    private PriceMapper priceMapper;

    public List<ProductDto> map(List<Product> products) {
        if (products == null) {
            return List.of();
        }
        List<ProductDto> result = new ArrayList<>();
        for (Product product : products) {
            ProductDto dto = map(product);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

    public ProductDto map(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrices(priceMapper.map(product.getPrices()));
        return dto;
    }
}
