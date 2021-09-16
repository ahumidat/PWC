package com.example.pwc.Services;

import com.example.pwc.Models.Users;

import java.util.*;

public interface EmployeeManagementService {
    public List<Users> getAll();
    public List<Users> getAllEmployees();
    public Users getUserByName(String name);
    public Users saveUser(Users user);
    public boolean delete(Users user);
}
