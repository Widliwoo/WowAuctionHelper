package com.example.auctionhelper.mapper;

import com.example.auctionhelper.dto.RealmDto;
import com.example.auctionhelper.entity.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RealmMapper {

    @Autowired
    private ProductMapper productMapper;

    public List<RealmDto> map(List<Realm> realms) {
        List<RealmDto> result = new ArrayList<>();
        if(realms != null) {
            for (Realm realm : realms) {
                RealmDto dto = map(realm);
                if (dto != null) {
                    result.add(dto);
                }
            }
        }
        return result;
    }

    public RealmDto map(Realm realm) {
        if (realm == null) {
            return null;
        }
        RealmDto dto = new RealmDto();
        dto.setName(realm.getFullName());
        dto.setMultiplier(realm.getMultiplier());
        dto.setProducts(productMapper.map(realm.getProducts()));
        return dto;
    }
}
