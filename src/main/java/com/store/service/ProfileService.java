package com.store.service;

import com.store.dto.CreateProfileDTO;
import com.store.dto.PaginationResultDTO;
import com.store.dto.ProfileDTO;
import com.store.dto.ProfileFilterDTO;
import com.store.entity.ProfileEntity;
import com.store.exp.AppBadException;
import com.store.repository.ProfileRepository;
import com.store.repository.custom.ProfileCustomRepository;
import com.store.util.JWTUtil;
import com.store.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(CreateProfileDTO dto) {
        checkLogin(dto.getLogin());
        isValidPassword(dto.getPassword());
        if (dto.getLogin() == null || dto.getName() == null || dto.getSurName() == null || dto.getRole() == null || dto.getStatus() == null) {
            throw new AppBadException("Some parameter is null. All fields must be given a value!!!");
        }
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity);
        return toDTO(entity);
    }

    private void checkLogin(String login) {
        if (profileRepository.findByLogin(login).isPresent()) {
            throw new AppBadException("There is a user with this login");
        }
    }

    private static ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setLogin(entity.getLogin());
        profileDTO.setRole(entity.getRole());
        profileDTO.setStatus(entity.getStatus());
        profileDTO.setCreatedDate(entity.getCreatedDate());
        profileDTO.setVisible(entity.getVisible());
        profileDTO.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));
        return profileDTO;
    }

    public static void isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new AppBadException("Not a valid password (Must contain an uppercase letter, a lowercase letter, and a number, length 8)");
        }
    }

    private static ProfileEntity toEntity(CreateProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurName());
        entity.setLogin(dto.getLogin());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public ProfileDTO update(CreateProfileDTO dto, String id) {
        checkLogin(dto.getLogin());
        ProfileEntity entity = get(id);
        return entityUpdate(dto, entity);
    }

    private ProfileDTO entityUpdate(CreateProfileDTO dto, ProfileEntity entity) {
        entity.setName(dto.getName() == null ? entity.getName() : dto.getName());
        entity.setSurname(dto.getSurName() == null ? entity.getSurname() : dto.getSurName());
        entity.setLogin(dto.getLogin() == null ? entity.getLogin() : dto.getLogin());
        entity.setRole(dto.getRole() == null ? entity.getRole() : dto.getRole());
        entity.setPassword(dto.getPassword() == null ? entity.getPassword() : MD5Util.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus() == null ? entity.getStatus() : dto.getStatus());
        entity.setUpdatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        return toDTO(entity);
    }

    public ProfileEntity get(String id) {
        Optional<ProfileEntity> entityOptional = profileRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        return entityOptional.get();
    }

    public List<ProfileDTO> getAll() {
        List<ProfileDTO> dtoList = new LinkedList<>();
        Iterable<ProfileEntity> entityIterable = profileRepository.findAll();
        for (ProfileEntity entity : entityIterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public Boolean deleteById(String id) {
        Optional<ProfileEntity> entityOptional = profileRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new AppBadException("No user found with this id");
        }
        profileRepository.deleteById(id);
        return true;
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO dto, Integer page, Integer size) {
        PaginationResultDTO<ProfileEntity> paginationResultDTO = profileCustomRepository.filter(dto, page, size);
        List<ProfileEntity> profileEntityList = paginationResultDTO.getList();
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : profileEntityList) {
            dtoList.add(toDTO(entity));
        }
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(dtoList, pageable, paginationResultDTO.getTotalSize());
    }

    public List<ProfileDTO> getActiveProfile() {
        List<ProfileDTO> dtoList = new LinkedList<>();

        // eng kup kitob sotib olgan profile
        ProfileEntity activePurchasesCountEntity = profileRepository.activePurchasesCount();
        dtoList.add(toDTO(activePurchasesCountEntity));

        //eng kup xarajat qilgan profile
        ProfileEntity activePricePurchases = profileRepository.activePricePurchases();
        dtoList.add(toDTO(activePricePurchases));

        //eng kup comment yozgan profile
        ProfileEntity activeCommentCount = profileRepository.activeCommentCount();
        dtoList.add(toDTO(activeCommentCount));
        return dtoList;
    }

    public void updateProfilePurchasesCount(String profileId) {
        ProfileEntity profileEntity = get(profileId);
        profileEntity.setPurchasesCount(profileEntity.getPurchasesCount() + 1);
        profileRepository.save(profileEntity);
    }

    public void updateProfileCommentCount(String profileId) {
        ProfileEntity profileEntity = get(profileId);
        profileEntity.setCommentCount(profileEntity.getCommentCount() + 1);
        profileRepository.save(profileEntity);
    }

    public void updateProfileAmountPurchases(String profileId, Double price) {
        ProfileEntity profileEntity = get(profileId);
        profileEntity.setPricePurchases(profileEntity.getPricePurchases() + price);
        profileRepository.save(profileEntity);
    }

    public ProfileDTO statistic(String id) {
        ProfileDTO profileDTO = new ProfileDTO();
        ProfileEntity profileEntity = get(id);
        profileDTO.setName(profileEntity.getName());
        profileDTO.setPurchasesCount(profileEntity.getPurchasesCount());
        profileDTO.setPricePurchases(profileEntity.getPricePurchases());
        profileDTO.setCommentCount(profileEntity.getCommentCount());
        return profileDTO;
    }
}
