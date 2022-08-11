package com.example.auctionhelper;

import com.example.auctionhelper.dto.RealmDto;
import com.example.auctionhelper.entity.Realm;
import com.example.auctionhelper.mapper.RealmMapper;
import com.example.auctionhelper.service.RealmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private RealmService realmService;

    @Autowired
    private RealmMapper realmMapper;

    @GetMapping
    public String index(Model model) {
        List<Realm> realms = realmService.getRealmsWithProducts();
        List<RealmDto> realmDtos = realmMapper.map(realms);
        model.addAttribute("realms", realmDtos);
        return "main/index";
    }
}
