package com.abhinav.Money_Manager.repository;

import com.abhinav.Money_Manager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity , Long>{

    // Select * from profiles_tables where email=?
    Optional<ProfileEntity>findByEmail(String email);
}
