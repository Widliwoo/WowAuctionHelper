package com.example.auctionhelper.repository;

import com.example.auctionhelper.entity.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {
}
