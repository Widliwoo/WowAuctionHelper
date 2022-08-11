package com.example.auctionhelper.service;

import com.example.auctionhelper.entity.Realm;
import com.example.auctionhelper.repository.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealmService {

    @Autowired
    private RealmRepository realmRepository;

    public List<Realm> getRealmsWithProducts() {
        List<Realm> realms = realmRepository.findAll();
        return realms;
    }
}
