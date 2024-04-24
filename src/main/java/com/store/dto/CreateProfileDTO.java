package com.store.dto;

import com.store.enums.ProfileRole;
import com.store.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfileDTO {
    private String name;
    private String surName;
    private String login;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private String jwt;
}
