package com.store.service;

import com.store.dto.AuthDTO;
import com.store.dto.ProfileDTO;
import com.store.dto.RegistrationDTO;
import com.store.entity.ProfileEntity;
import com.store.enums.ProfileRole;
import com.store.enums.ProfileStatus;
import com.store.exp.AppBadException;
import com.store.repository.ProfileRepository;
import com.store.util.JWTUtil;
import com.store.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO auth(AuthDTO profile) {
        Optional<ProfileEntity> optional = profileRepository.findByLoginAndPassword(profile.getLogin(), MD5Util.encode(profile.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("Profile not active");
        }
        ProfileEntity entity = optional.get();
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getLogin(), entity.getRole()));
        return dto;
    }

    public String registration(RegistrationDTO dto) {

        checkLogin(dto);

        isValidPassword(dto.getPassword());

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setLogin(dto.getLogin());
        profileRepository.save(entity);

        return "Successful";
    }

    private void checkLogin(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByLogin(dto.getLogin());
        if (optional.isPresent()) {
            throw new AppBadException("There is a user with such a login");
        }
    }

    /**
     * Password ni 1 ta kichik harf
     * 1 ta katta harf
     * uzunligi 8 dan kattaligi
     * 1 ta raqam borligini tekshirish
     */
    public static void isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new AppBadException("Not a valid password (Must contain an uppercase letter, a lowercase letter, and a number, length 8)");
        }
    }
}
