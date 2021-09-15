package com.example.pwc.Controllers;

import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/rest/register")
public class RegistrationController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/submit")
    public Users registerUser(@Valid @RequestBody Users user){
       if (userAlreadyExists(user)){
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please use another email or username");
       }
       if(!validateParams(user)){
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please make sure that you passed a vaild email, and that username and password are filled");
       }
       user.setRole(Role.Employee.name());
       return userRepo.save(user);
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
        List<Users> allUsers = userRepo.findAll();
        return allUsers.contains(user);
    }

}
