package com.example.pwc.Models;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    private String name;
    private String password;
    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    private Department department;
    private String role;
    @OneToMany(mappedBy = "user")
    Set<ProjectsEmployees> projects;


    public User(long ID, String name, Department department, String role) {
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.role = role;
    }

    public User() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<ProjectsEmployees> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectsEmployees> registrations) {
        this.projects = registrations;
    }
}
