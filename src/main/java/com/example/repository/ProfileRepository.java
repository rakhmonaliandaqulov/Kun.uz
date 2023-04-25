package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String login, String md5Hash, boolean b);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

    Page<ProfileEntity> findAll(Pageable paging);

    Optional<ProfileEntity> findByEmail(String email);
}
