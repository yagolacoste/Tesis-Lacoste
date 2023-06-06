package com.Tesis.admin.Repository;

import com.Tesis.admin.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

  User findByEmail(String email);

  Boolean existsByEmail(String email);
}
