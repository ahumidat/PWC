package com.example.pwc.Models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    private String name;
    private String password;
    @ManyToOne
    private Department department;
    private String role;
    private String email;
    @ManyToMany
    @JoinTable(name = "project_employee_relation", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<Project> projects;


    public Users(long ID, String name, Department department, String role) {
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.role = role;
    }

    public Users() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Users))
            return false;
        return ((Users) o).name.equals(this.name) || ((Users) o).email.equals(this.email);
    }
}
