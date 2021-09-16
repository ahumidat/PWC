package com.example.pwc.Services.impl;

import com.example.pwc.Models.Department;
import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import com.example.pwc.Services.EmployeeManagementService;
import com.example.pwc.Utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    @Autowired
    UserRepository userRepo;

    @Override
    public List<Users> getAll() {
        return userRepo.findAll();
    }

    @Override
    public List<Users> getAllEmployees() {
        return userRepo.findUsersByRole(Role.Employee.name());
    }

    @Override
    public Users getUserByName(String name) {
        return userRepo.findUsersByUsername(name);
    }

    @Override
    public Users saveUser(Users user){
        return userRepo.save(user);
    }

    @Override
    public boolean delete(Users user){
        Users UserEntity = userRepo.findUsersByUsername(user.getUsername());
        if (UserEntity != null){
            userRepo.delete(UserEntity);
            return true;
        }
        return false;
    }
}
