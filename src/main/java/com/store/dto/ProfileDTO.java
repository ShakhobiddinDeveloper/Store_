package com.store.dto;

import com.store.enums.ProfileRole;
import com.store.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    protected String id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
    private Boolean visible;
    private String jwt;
    // necha marotaba kitob sotib olgani
    private Long purchasesCount;
    // qancha xarajat qilgani
    private Double pricePurchases;
    // nechta kommment yozgani
    private Long commentCount;
}
