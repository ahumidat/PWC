package com.example.pwc.Repositories;

import com.example.pwc.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findProjectByName(String name);
}
