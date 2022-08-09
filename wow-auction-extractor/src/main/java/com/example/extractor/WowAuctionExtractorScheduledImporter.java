package com.example.extractor;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.dto.RealmDto;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.service.ImportService;
import com.example.extractor.service.RealmFacade;
import com.example.extractor.util.ChecksumUtills;
import com.example.extractor.service.LuaExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class WowAuctionExtractorScheduledImporter {

    private static final String ACC_NAME_PLACEHOLDER = "${accountName}";
    private static final String SOURCE_RELATIVE_NAME = "WTF/Account/" + ACC_NAME_PLACEHOLDER + "/SavedVariables/Auctionator.lua";
    private static final String DEFAULT_DB_VAR_NAME = "AUCTIONATOR_PRICE_DATABASE";

    @Value("${wow.rootFolder}")
    private String rootFolder;

    @Value("${wow.accName}")
    private String accName;

    @Autowired
    private LuaExecutor luaExecutor;

    @Autowired
    private RealmFacade realmFacade;

    @Autowired
    private ImportService importService;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void importData() throws Exception {
        File source = new File(buildFileNameFor(rootFolder, accName));
        ImportDto importDto = createImportFrom(source);
        boolean isRelevant = importService.checkRelevanceFor(importDto);
        if (isRelevant) {
            List<RealmDto> realms = luaExecutor.importRealms();
            realmFacade.saveRealms(realms, importDto);
        } else {
            log.info("#importData interrupted. Reason is: irrelevant file.");
        }
        log.info("#importData execution completed.");
    }

    private ImportDto createImportFrom(File source) {
        ImportDto dto = new ImportDto();
        dto.setStartDate(LocalDateTime.now());
        dto.setStatus(ImportStatus.STARTED);
        try {
            dto.setMd5(ChecksumUtills.md5(source));
            dto.setSha256(ChecksumUtills.sha256(source));
        } catch (IOException e) {
            log.error("WowAuctionExtractorScheduledImporter #calculateCheckSums cannot calculate sum for file: {}.Reason is: {}",
                    source.getAbsolutePath(),
                    e.getMessage());
            throw new RuntimeException(e);
        }
        return dto;
    }

    private String buildFileNameFor(String wowRootFolder, String accountName) {
        String firstPart = "";
        if (checkOrCharAtEqualTo(wowRootFolder, wowRootFolder.length() - 1, '/', '\\')) {
            firstPart = wowRootFolder.substring(0, wowRootFolder.length() - 1);
        } else {
            firstPart = wowRootFolder;
        }
        return firstPart + "/" + SOURCE_RELATIVE_NAME.replace(ACC_NAME_PLACEHOLDER, accountName);
    }

    private boolean checkOrCharAtEqualTo(String str, int index, char... chars) {
        boolean equal = false;
        for (char c : chars) {
            equal = equal || (str.charAt(index) == c);
        }
        return equal;
    }
}
