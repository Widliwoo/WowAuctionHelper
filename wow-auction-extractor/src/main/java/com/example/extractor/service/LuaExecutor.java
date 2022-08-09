package com.example.extractor.service;

import com.example.extractor.dto.RealmDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class LuaExecutor {

    private static final String ACC_NAME_PLACEHOLDER = "${accountName}";
    private static final String SOURCE_RELATIVE_PATH = "WTF/Account/" + ACC_NAME_PLACEHOLDER + "/SavedVariables/Auctionator.lua";
    private static final String DEFAULT_DB_VAR_NAME = "AUCTIONATOR_PRICE_DATABASE";

    @Value("${wow.rootFolder}")
    private String rootFolder;

    @Value("${wow.accName}")
    private String accName;

    private String auctionatorLuaPath;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        auctionatorLuaPath = (
                rootFolder + "/" + SOURCE_RELATIVE_PATH.replace(ACC_NAME_PLACEHOLDER, accName)
        ).replace("//", "/");
    }

    public List<RealmDto> importRealms() throws JsonProcessingException {
        Globals globals = JsePlatform.standardGlobals();
        globals.loadfile(auctionatorLuaPath).call();
        globals.loadfile(getClass().getClassLoader().getResource("AuctionatorExporter.lua").getFile()).call();
        String resultingJson = globals.load(String.format("return exportJson(%s)", DEFAULT_DB_VAR_NAME)).call().checkjstring();
        return objectMapper.readValue(resultingJson, new TypeReference<List<RealmDto>>() {
        });
    }
}
