package com.example.pwc.Services.impl;

import com.example.pwc.Models.Department;
import com.example.pwc.Repositories.DepartmentRepository;
import com.example.pwc.Repositories.ProjectRepository;
import com.example.pwc.Services.DepartmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentManagementServiceImpl implements DepartmentManagementService {

    @Autowired
    DepartmentRepository departmentRepo;


    @Override
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Department create(Department d) {
        return departmentRepo.save(d);
    }

    @Override
    public boolean delete(Department d) {
        Department departmentEntity = departmentRepo.findDepartmentByName(d.getName());
        if (departmentEntity != null){
            if (! departmentEntity.getUsers().isEmpty()){
                return false;
            }
            departmentRepo.delete(departmentEntity);
            return true;
        }
        return false;
    }

    @Override
    public Department getByName(String dName) {
        return departmentRepo.findDepartmentByName(dName);
    }
}
