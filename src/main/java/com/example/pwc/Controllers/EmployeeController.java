package com.example.pwc.Controllers;

import com.example.pwc.Models.Project;
import com.example.pwc.Models.Users;
import com.example.pwc.Services.EmployeeManagementService;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import com.example.pwc.Utils.auth.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rest/profile")
public class EmployeeController {
    @Autowired
    EmployeeManagementService employeeSvc;
    @Autowired
    Authenticate authenticate;

    @GetMapping
    public Users getProfile(@RequestHeader("authorization") String auth ){
        Users authU = authenticate.authenticateUser(auth);
        if (authU != null){
            return authU;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
