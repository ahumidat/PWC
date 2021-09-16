package com.example.pwc.Repositories;

import com.example.pwc.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentByName(String name);
}
