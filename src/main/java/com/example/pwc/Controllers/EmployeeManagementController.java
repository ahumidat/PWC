package com.example.pwc.Controllers;

import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import com.example.pwc.Services.EmployeeManagementService;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/rest/employee")
public class EmployeeManagementController {
    @Autowired
    EmployeeManagementService employeeSvc;

    @GetMapping("/list")
    public List<Users> getEmployees(){
        return employeeSvc.getAllEmployees();
    }

    @PostMapping("/create")
    public Users createEmployee(@Valid @RequestBody Users user){
        if (userAlreadyExists(user)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please use another email or username");
        }
        if(!validateParams(user)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please make sure that you passed a vaild email, and that username and password are filled");
        }

        return  employeeSvc.saveUser(user);
    }

    private boolean validateParams(Users user) {
        if (ValidationUtils.isEmpty(user.getUsername()) || ValidationUtils.isEmpty(user.getPassword()) || ValidationUtils.isEmpty(user.getEmail())){
            return false;
        }
        if(!ValidationUtils.isValidEmail(user.getEmail())){
            return false;
        }
        return true;
    }

    private boolean userAlreadyExists(Users user) {
        List<Users> allUsers = employeeSvc.getAll();
        return allUsers.contains(user);
    }

}
