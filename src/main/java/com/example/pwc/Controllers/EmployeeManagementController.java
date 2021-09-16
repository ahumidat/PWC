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
import com.example.pwc.Utils.auth.Authenticate;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rest/employee")
public class EmployeeManagementController {
    @Autowired
    EmployeeManagementService employeeSvc;
    @Autowired
    DepartmentManagementService departmentSvc;
    @Autowired
    ProjectManagementService projectSvc;
    @Autowired
    Authenticate authenticate;

    @GetMapping("/list")
    public List<Users> getEmployees(@RequestHeader("authorization") String auth, @RequestParam(required = false) String name,@RequestParam(required = false) String email,
                                   @RequestParam(required = false) String department, @RequestParam(required = false) String project ){
        Users authU = authenticate.authenticateUser(auth);
        if (authU != null &&  authU.getRole().equals(Role.Manager.name())){
            List<Users> employees = employeeSvc.getAllEmployees();
            if(! ValidationUtils.isEmpty(name)){
                employees = employees.stream().filter(u -> u.getUsername().equals(name)).collect(Collectors.toList());
            }
            if (! ValidationUtils.isEmpty(email)){
                employees = employees.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
            }
            if (! ValidationUtils.isEmpty(department)){
                employees = employees.stream().filter(u -> u.getDepartment().getName().equalsIgnoreCase(department)).collect(Collectors.toList());
            }
            if (! ValidationUtils.isEmpty(project)){
                Project p = projectSvc.getProjectByName(project);
                employees = employees.stream().filter(u -> u.getProjects().contains(p)).collect(Collectors.toList());
            }
            return employees;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create")
    public Users createEmployee(@RequestHeader("authorization") String auth, @Valid @RequestBody Users user){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (userAlreadyExists(user)){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please use another email or username");
            }
            if(!validateParams(user)){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please make sure that you passed a vaild email, and that username and password are filled");
            }

            return  employeeSvc.saveUser(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/role/{role}")
    ResponseEntity<?> updateRole(@RequestHeader("authorization") String auth, @RequestBody @Valid Users emp, @PathVariable String role) {

        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
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
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/project/add/{projectName}")
    ResponseEntity<?> assignProject(@RequestHeader("authorization") String auth, @RequestBody @Valid Users emp, @PathVariable String projectName){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
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
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/project/remove/{projectName}")
    ResponseEntity<?> removeProject(@RequestHeader("authorization") String auth, @RequestBody @Valid Users emp, @PathVariable String projectName){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
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
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/department/update/{depName}")
    ResponseEntity<?> updateDepartment(@RequestHeader("authorization") String auth, @RequestBody @Valid Users emp, @PathVariable String depName){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            Users updatedEmployee = employeeSvc.getUserByName(emp.getUsername()) ;
            if (updatedEmployee == null){
                return new ResponseEntity<>("This Employee doesn't exist",HttpStatus.NOT_FOUND);
            }
//        Check Department exists
            Department d = departmentSvc.getByName(depName);
            if (d == null){
                return new ResponseEntity<>("This Department doesn't exist",HttpStatus.NOT_FOUND);
            }

            if (updatedEmployee.getDepartment().getName().equalsIgnoreCase(depName)){
                return new ResponseEntity<>("User is already assigned to this department",HttpStatus.NOT_MODIFIED);
            }

            updatedEmployee.setDepartment(d);

            employeeSvc.saveUser(updatedEmployee);
            return new ResponseEntity<>("Department was updated successfully",HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/department/remove")
    ResponseEntity<?> removeDepartment(@RequestHeader("authorization") String auth, @RequestBody @Valid Users emp){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            Users updatedEmployee = employeeSvc.getUserByName(emp.getUsername()) ;
            if (updatedEmployee == null){
                return new ResponseEntity<>("This Employee doesn't exist",HttpStatus.NOT_FOUND);
            }

            if (updatedEmployee.getRole().equals(Role.Employee.name())){
                return new ResponseEntity<>("Employee must be assigned to department",HttpStatus.FORBIDDEN);
            }

            updatedEmployee.setDepartment(null);

            employeeSvc.saveUser(updatedEmployee);
            return new ResponseEntity<>("Department was removed successfully",HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@RequestHeader("authorization") String auth, @Valid @RequestBody Users e){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (employeeSvc.delete(e)){
                return new ResponseEntity<>("The project deleted", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private boolean userAlreadyExists(Users user) {
        List<Users> allUsers = employeeSvc.getAll();
        return allUsers.contains(user);
    }

}
