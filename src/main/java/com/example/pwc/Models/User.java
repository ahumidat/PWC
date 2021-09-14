package com.example.pwc.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long ID;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "department",referencedColumnName = "name")
    private Department department;
    @Column(name = "role")
    private String role;
    @ManyToMany
    private List<Project> projects;


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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
