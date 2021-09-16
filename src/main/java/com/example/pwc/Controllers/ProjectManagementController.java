package com.example.pwc.Controllers;

import com.example.pwc.Models.Project;
import com.example.pwc.Models.Users;
import com.example.pwc.Services.ProjectManagementService;
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
@RequestMapping("/api/v1/rest/project")
public class ProjectManagementController {

    @Autowired
    ProjectManagementService projectSvc;
    @Autowired
    Authenticate authenticate;

    @GetMapping("/list")
    public List<Project> getProjects(@RequestHeader("authorization") String auth){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            return projectSvc.getAll();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create")
    public Project createProject(@RequestHeader("authorization") String auth, @Valid @RequestBody Project p ){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (ValidationUtils.isEmpty(p.getName()) || ValidationUtils.isEmpty(p.getDescription())){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please pass project name and description");
            }
            return projectSvc.create(p);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProject(@RequestHeader("authorization") String auth, @Valid @RequestBody Project project){
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            if (projectSvc.delete(project)){
                return new ResponseEntity<>("The project deleted", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/desc/{desc}")
    ResponseEntity<?> updateDesc(@RequestHeader("authorization") String auth, @RequestBody @Valid Project p, @PathVariable String desc) {
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            Project project = projectSvc.getProjectByName(p.getName()) ;
            if (project == null){
                return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
            }
            if (ValidationUtils.isEmpty(desc)){
                return new ResponseEntity<>("Passed description is empty.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
            project.setDescription(desc);
            projectSvc.create(project);
            return new ResponseEntity<>("Project description updated successfully",HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/name/{name}")
    ResponseEntity<?> updateName(@RequestHeader("authorization") String auth, @RequestBody @Valid Project p, @PathVariable String name) {
        Users u = authenticate.authenticateUser(auth);
        if (u != null &&  u.getRole().equals(Role.Manager.name())){
            Project project = projectSvc.getProjectByName(p.getName()) ;
            if (project == null){
                return new ResponseEntity<>("This Project doesn't exist",HttpStatus.NOT_FOUND);
            }
            if (ValidationUtils.isEmpty(name)){
                return new ResponseEntity<>("Passed name is empty.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
            project.setName(name);
            projectSvc.create(project);
            return new ResponseEntity<>("Project Name updated successfully",HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
