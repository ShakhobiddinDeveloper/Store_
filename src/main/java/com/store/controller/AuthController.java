package com.store.controller;

import com.store.dto.AuthDTO;
import com.store.dto.ProfileDTO;
import com.store.dto.RegistrationDTO;
import com.store.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.warn("Login {} ", dto.getLogin());
        return ResponseEntity.ok(authService.auth(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("registration {} ", dto.getLogin());
        return ResponseEntity.ok(authService.registration(dto));
    }

}
