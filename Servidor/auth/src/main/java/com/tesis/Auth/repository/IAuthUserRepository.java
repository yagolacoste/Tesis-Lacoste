package com.tesis.Auth.repository;

import com.tesis.Auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthUserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

//    Optional<User> findByUsername(String username);

//    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
