package com.example.extractor.service;

import com.example.extractor.LuaCodeContainer;
import com.example.extractor.dto.RealmDto;
import com.example.extractor.event.ImportFailedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
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

    public List<RealmDto> importRealms(String source) throws JsonProcessingException {
        Globals globals = JsePlatform.standardGlobals();
        globals.load(source).call();
        globals.load(LuaCodeContainer.LUA_EXTRACTING_FUNCTIONS).call();
        String resultingJson = globals.load(LuaCodeContainer.LUA_EXTRACT_DATA).call().checkjstring();
        return objectMapper.readValue(resultingJson, new TypeReference<>() {
        });
    }

    public String getWowLuaFileContent() {
        File file = new File(auctionatorLuaPath);
        try(FileInputStream fis = new FileInputStream(file)) {
            return  new String(fis.readAllBytes());
        } catch (IOException e) {
            log.error("#getWowLuaFileContent error reading file content of {}. Reason is {}", auctionatorLuaPath, e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
