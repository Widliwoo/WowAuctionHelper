package com.example.extractor.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@FieldNameConstants
public class Import extends IdentifiableEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    private String sha256;
    private String md5;

    private Boolean sent = Boolean.FALSE;

    @OneToMany
    private List<Price> priceList;
}
