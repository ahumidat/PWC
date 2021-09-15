package com.example.pwc.Services;

import com.example.pwc.Models.Project;

import java.util.List;

public interface ProjectManagementService {
    public List<Project> getAll();
    public Project create(Project p);
    public boolean delete(Project p);
}
