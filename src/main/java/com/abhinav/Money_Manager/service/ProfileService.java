package com.abhinav.Money_Manager.service;

import com.abhinav.Money_Manager.dto.ProfileDTO;
import com.abhinav.Money_Manager.entity.ProfileEntity;
import com.abhinav.Money_Manager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final EmailService emailService ;

    // to register a new account on the platform
    public ProfileDTO registerProfile(ProfileDTO profileDTO){
        ProfileEntity newprofile = toEntity(profileDTO);

       // to create a activation token and link for email service
        newprofile.setActivationToken(UUID.randomUUID().toString());
        newprofile =  profileRepository.save(newprofile);
        String activationLink = "http://localhost:8091/activate?token=" + newprofile.getActivationToken();
        String sub = "Account Verification & Activation Email" ;
        String message = "Click on the below activation link for successfully activation of your account \n\n" + activationLink ;
        emailService.sendEmail(newprofile.getEmail() , sub , message);
        return toDTO(newprofile);
    }

    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(profileDTO.getPassword())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }

    public boolean activateProfile(String activationToken){
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true ;
                })
                .orElse(false);
    }
}
