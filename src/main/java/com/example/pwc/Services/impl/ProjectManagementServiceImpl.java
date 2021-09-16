package com.example.pwc.Services.impl;

import com.example.pwc.Models.Project;
import com.example.pwc.Repositories.ProjectRepository;
import com.example.pwc.Services.ProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {

    @Autowired
    ProjectRepository projectRepo;


    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    @Override
    public Project create(Project p) {
        return projectRepo.save(p);
    }

    @Override
    public boolean delete(Project p) {
        Project projectEntity = projectRepo.findProjectByName(p.getName());
        if (projectEntity != null){
            projectRepo.delete(projectEntity);
            return true;
        }
        return false;
    }

    @Override
    public Project getProjectByName(String name){
        return projectRepo.findProjectByName(name);
    }
}
