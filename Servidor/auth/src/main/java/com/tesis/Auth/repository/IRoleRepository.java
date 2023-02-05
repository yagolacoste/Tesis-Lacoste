package com.tesis.Auth.repository;

import com.tesis.Auth.models.Role;
import com.tesis.Auth.config.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);

}
