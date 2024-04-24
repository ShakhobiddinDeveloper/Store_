package com.store.config;

import com.store.entity.ProfileEntity;
import com.store.exp.AppBadException;
import com.store.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // login/password
        Optional<ProfileEntity> optional = profileRepository.findByLogin(username);
        if (optional.isEmpty()) {
            throw new AppBadException("Bad Credentials.");
        }

        ProfileEntity profile = optional.get();
        return new CustomUserDetails(profile.getId(), profile.getLogin(),
                profile.getPassword(), profile.getStatus(), profile.getRole());
    }

}
