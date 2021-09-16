package com.example.pwc.Controllers;

import com.example.pwc.Models.Department;
import com.example.pwc.Models.Project;
import com.example.pwc.Services.DepartmentManagementService;
import com.example.pwc.Services.ProjectManagementService;
import com.example.pwc.Utils.ValidationUtils;
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

    @GetMapping("/list")
    public List<Department> getProjects(){
        return svc.getAll();
    }

    @PostMapping("/create")
    public Department createDepartment(@Valid @RequestBody Department d ){
        if (ValidationUtils.isEmpty(d.getName())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please pass Department name");
        }
        return svc.create(d);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProject(@Valid @RequestBody Department d){
        if (svc.delete(d)){
            return new ResponseEntity<>("The department deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("This department doesn't exist",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/name/{name}")
    ResponseEntity<?> updateName(@RequestBody @Valid Department d, @PathVariable String name) {

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
}
