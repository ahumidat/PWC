package com.example.pwc.Models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Project {
    @Column(name = "id")
    private @Id long id;
    @Column(name = "name")
    private String name;
    @Column(name = "desc")
    private String description;
    @ManyToMany
    private List<User> employees;

    public Project(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Project(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }
}

