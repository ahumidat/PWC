package com.example.pwc.Repositories;

import com.example.pwc.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<Users, Long> {
}
