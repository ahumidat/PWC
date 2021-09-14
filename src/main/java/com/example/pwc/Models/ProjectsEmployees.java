package com.example.pwc.Models;

import javax.persistence.*;

@Entity
public class ProjectsEmployees {

    private @Id long id;
    @ManyToOne
    @JoinColumn(name = "user")
    User user;
    @ManyToOne
    @JoinColumn(name = "project")
    Project project;

    public ProjectsEmployees(long id, User user, Project project) {
        this.id = id;
        this.user = user;
        this.project = project;
    }

    public ProjectsEmployees() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
