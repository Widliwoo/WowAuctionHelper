package com.example.extractor.service;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.dto.ProductDto;
import com.example.extractor.dto.RealmDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.Price;
import com.example.extractor.repository.ImportRepository;
import com.example.extractor.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RealmFacade {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ImportRepository importRepository;

    @Transactional
    public void saveRealms(List<RealmDto> realms, ImportDto importDto) {
        List<Price> prices = new ArrayList<>();
        for (RealmDto realm : realms) {
            for (ProductDto product : realm.getProducts()) {
                Price price = new Price();
                price.setProductPrice(product.getPrice());
                price.setProductName(product.getName());
                price.setRealmName(realm.getName());
                prices.add(price);
            }
        }

        List<Price> priceList = priceRepository.saveAllAndFlush(prices);
        Import entity = importRepository.findById(importDto.getId()).get();
        entity.setPriceList(priceList);
        importRepository.save(entity);
    }
}
