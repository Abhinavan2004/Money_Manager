package com.abhinav.Money_Manager.service;

import com.abhinav.Money_Manager.entity.ProfileEntity;
import com.abhinav.Money_Manager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity exisitingProfile = profileRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));
        return User.builder()
                .username(exisitingProfile.getEmail())
                .password(exisitingProfile.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
