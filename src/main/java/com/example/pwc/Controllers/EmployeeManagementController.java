package com.example.pwc.Controllers;

import com.example.pwc.Models.Department;
import com.example.pwc.Models.Project;
import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import com.example.pwc.Services.DepartmentManagementService;
import com.example.pwc.Services.EmployeeManagementService;
import com.example.pwc.Services.ProjectManagementService;
import com.example.pwc.Utils.Role;
import com.example.pwc.Utils.ValidationUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/rest/employee")
public class EmployeeManagementController {
    @Autowired
    EmployeeManagementService employeeSvc;
    @Autowired
    DepartmentManagementService departmentSvc;
    @Autowired
    ProjectManagementService projectSvc;

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

    @PutMapping("/role/{role}")
    ResponseEntity<?> updateRole(@RequestBody @Valid Users emp, @PathVariable String role) {

        Users updatedEmployee = employeeSvc.getUserByName(emp.getUsername()) ;
        if (updatedEmployee == null){
            return new ResponseEntity<>("This Employee doesn't exist",HttpStatus.NOT_FOUND);
        }
        if (! ValidationUtils.isValidRole(role)){
            return new ResponseEntity<>("Passed role is invalid. Users can be either \'Employee\' or \'Manager\'",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        updatedEmployee.setRole(role);
        employeeSvc.saveUser(updatedEmployee);
        return new ResponseEntity<>("Role updated successfully",HttpStatus.OK);
    }

    @PutMapping("/project/add/{projectName}")
    ResponseEntity<?> assignProject(@RequestBody @Valid Users emp, @PathVariable String projectName){
        Users updatedEmployee = employeeSvc.getUserByName(emp.getUsername()) ;
        if (updatedEmployee == null){
            return new ResponseEntity<>("This Employee doesn't exist",HttpStatus.NOT_FOUND);
        }
//        Check project exists
        Project p = projectSvc.getProjectByName(projectName);
        if (p == null){
            return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
        }

        if (updatedEmployee.getProjects().contains(p)){
            return new ResponseEntity<>("Project is already assigned to this employee",HttpStatus.NOT_MODIFIED);
        }
        updatedEmployee.getProjects().add(p);
        employeeSvc.saveUser(updatedEmployee);
        return new ResponseEntity<>("Project was assigned to the passed user successfully",HttpStatus.OK);
    }

    @PutMapping("/project/remove/{projectName}")
    ResponseEntity<?> removeProject(@RequestBody @Valid Users emp, @PathVariable String projectName){
        Users updatedEmployee = employeeSvc.getUserByName(emp.getUsername()) ;
        if (updatedEmployee == null){
            return new ResponseEntity<>("This Employee doesn't exist",HttpStatus.NOT_FOUND);
        }
//        Check project exists
        Project p = projectSvc.getProjectByName(projectName);
        if (p == null){
            return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
        }

        if (updatedEmployee.getProjects().contains(p)){
            updatedEmployee.getProjects().remove(p);
        }
        employeeSvc.saveUser(updatedEmployee);
        return new ResponseEntity<>("Project was removed successfully",HttpStatus.OK);
    }

    private boolean validateParams(Users user) {
        if (ValidationUtils.isEmpty(user.getUsername()) || ValidationUtils.isEmpty(user.getPassword()) || ValidationUtils.isEmpty(user.getEmail())){
            return false;
        }
        if(!ValidationUtils.isValidEmail(user.getEmail())){
            return false;
        }
        if (! ValidationUtils.isValidRole(user.getRole())){
            return false;
        }
        if (user.getDepartment() == null){
            return false;
        }else{
            Department departmentEntity = departmentSvc.getByName(user.getDepartment().getName());
            if ( departmentEntity== null){
                return false;
            }else{
                user.setDepartment(departmentEntity);
            }
        }
        return true;
    }

    private boolean userAlreadyExists(Users user) {
        List<Users> allUsers = employeeSvc.getAll();
        return allUsers.contains(user);
    }

}
