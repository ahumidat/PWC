package com.example.pwc.Controllers;

import com.example.pwc.Models.Project;
import com.example.pwc.Models.Users;
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
@RequestMapping("/api/v1/rest/project")
public class ProjectManagementController {

    @Autowired
    ProjectManagementService projectSvc;

    @GetMapping("/list")
    public List<Project> getProjects(){
        return projectSvc.getAll();
    }

    @PostMapping("/create")
    public Project createProject(@Valid @RequestBody Project p ){
        if (ValidationUtils.isEmpty(p.getName()) || ValidationUtils.isEmpty(p.getDescription())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please pass project name and description");
        }
        return projectSvc.create(p);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProject(@Valid @RequestBody Project project){
        if (projectSvc.delete(project)){
            return new ResponseEntity<>("The project deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
        }
    }


}
