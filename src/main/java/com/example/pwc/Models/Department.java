package com.example.pwc.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`Department`")
public class Department {
    private @Id long id;
    private String name;
    @OneToMany(mappedBy = "department")
    Set<User> employees;

    public Department(long id, String name){
        this.name = name;
        this.id = id;
    }

    public Department(){}

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

    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> registrations) {
        this.employees = registrations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Department))
            return false;
        return ((Department) o).name.equals(this.name);
    }
}