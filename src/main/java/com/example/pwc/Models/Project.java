package com.example.pwc.Models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Project {
    private @Id long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "projects")
    Set<Users> employees;

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
}

