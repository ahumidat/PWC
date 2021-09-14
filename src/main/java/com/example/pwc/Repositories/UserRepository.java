package com.example.pwc.Repositories;

import com.example.pwc.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
}
