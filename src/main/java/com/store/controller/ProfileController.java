package com.store.controller;

import com.store.dto.CreateProfileDTO;
import com.store.dto.ProfileDTO;
import com.store.dto.ProfileFilterDTO;
import com.store.service.ProfileService;
import com.store.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProfileDTO> create(@RequestBody CreateProfileDTO dto) {
        log.warn("Profile create {}", dto.getLogin());
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProfileDTO> update(@RequestBody CreateProfileDTO dto,
                                             @PathVariable String id) {
        log.warn("Profile admin update {}", id);
        return ResponseEntity.ok(profileService.update(dto, id));
    }

    @PutMapping("/detail")
    public ResponseEntity<ProfileDTO> update(@RequestBody CreateProfileDTO dto) {
        log.warn("Profile detail update{}", SpringSecurityUtil.getCurrentUser().getLogin());
        return ResponseEntity.ok(profileService.update(dto, SpringSecurityUtil.getCurrentUser().getId()));
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        log.warn("Profile get all{}", SpringSecurityUtil.getCurrentUser().getLogin());
        return ResponseEntity.ok(profileService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String id) {
        log.warn("delete by id{}", SpringSecurityUtil.getCurrentUser().getLogin());
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    // filter vaqt, role , name va surname boyicha
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "5") Integer size) {
        log.warn("filter{}", SpringSecurityUtil.getCurrentUser().getId());
        return ResponseEntity.ok(profileService.filter(dto, page - 1, size));
    }

    //.Eng aktiv userlar
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<ProfileDTO>> activeProfile() {
        return ResponseEntity.ok(profileService.getActiveProfile());
    }

    //4.Vali(Ismi) 2(kitob soni) 89,000(summa) 21(comment soni)
    // Ali 5 53,000 45
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/statistic/{id}")
    public ResponseEntity<?> statistic(@PathVariable String id) {
        return ResponseEntity.ok(profileService.statistic(id));
    }

}
