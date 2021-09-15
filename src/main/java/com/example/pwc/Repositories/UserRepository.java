package com.example.pwc.Repositories;

import com.example.pwc.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findUsersByRole(String role);
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users findUsersByUsername(@Param("username") String username);
}
