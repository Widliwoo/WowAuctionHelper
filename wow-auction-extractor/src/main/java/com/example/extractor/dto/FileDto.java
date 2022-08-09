package com.example.extractor.dto;

import com.example.extractor.entity.FileEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class FileDto {
    private String md5;
    private String sha256;
    private LocalDateTime scanDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto fileDto = (FileDto) o;
        return Objects.equals(md5, fileDto.md5) && Objects.equals(sha256, fileDto.sha256);
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5, sha256);
    }

    public static FileDto from(FileEntity fileEntity) {
        FileDto dto = new FileDto();
        dto.setSha256(fileEntity.getSha256());
        dto.setMd5(fileEntity.getMd5());
        dto.setScanDate(fileEntity.getScanDate());
        return dto;
    }
}
