package com.example.pwc.Controllers;

import com.example.pwc.Models.Department;
import com.example.pwc.Models.Users;
import com.example.pwc.Services.DepartmentManagementService;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import com.example.pwc.Utils.auth.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rest/department")
public class DepartmentManagementController {

    @Autowired
    DepartmentManagementService svc;
    @Autowired
    Authenticate authenticate;

    @GetMapping("/list")
    public List<Department> getDepartments(@RequestHeader("authorization") String auth){
        Users u = authenticate.authenticateUser(auth);
        if (u != null && u.getRole().equals(Role.Manager.name())){
            return svc.getAll();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create")
    public Department createDepartment(@RequestHeader("authorization") String auth, @Valid @RequestBody Department d ){

        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (ValidationUtils.isEmpty(d.getName())){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please pass Department name");
            }
            return svc.create(d);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProject(@RequestHeader("authorization") String auth, @Valid @RequestBody Department d){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (svc.delete(d)){
                return new ResponseEntity<>("The department deleted", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("This department doesn't exist",HttpStatus.NOT_FOUND);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/name/{name}")
    ResponseEntity<?> updateName(@RequestHeader("authorization") String auth, @RequestBody @Valid Department d, @PathVariable String name) {
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            Department department = svc.getByName(d.getName()) ;
            if (department == null){
                return new ResponseEntity<>("This department doesn't exist",HttpStatus.NOT_FOUND);
            }
            if (ValidationUtils.isEmpty(name)){
                return new ResponseEntity<>("Passed name is empty.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
            department.setName(name);
            svc.create(department);
            return new ResponseEntity<>("Department Name updated successfully",HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
