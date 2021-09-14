package com.example.pwc.Controllers;

import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/rest/register")
public class RegistrationController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/submit")
    public String registerUser(@RequestBody Users user){
        if(user == null){
            return "Please fill in username and password";
        }
       if (userAlreadyExists(user)){
           return "Username passed is already reserved, please pick another name";
       }
       if(!validateParams(user)){
           return "Your input is Invalid, please make sure you filled all required data and that your email is valid";
       }
       user.setRole(Role.Employee.name());
       userRepo.save(user);
       return "User created successfully";
    }

    private boolean validateParams(Users user) {
        if (ValidationUtils.isEmpty(user.getName()) || ValidationUtils.isEmpty(user.getPassword()) || ValidationUtils.isEmpty(user.getEmail())){
            return false;
        }
        if(!ValidationUtils.isValidEmail(user.getEmail())){
            return false;
        }
        return true;
    }

    private boolean userAlreadyExists(Users user) {
        List<Users> allUsers = userRepo.findAll();
        return allUsers.contains(user);
    }

}
