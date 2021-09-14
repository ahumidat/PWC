package com.example.pwc.Models;

import javax.persistence.*;

@Entity
@Table(name = "`Department`")
public class Department {
    @Column(name = "id")
    private @Id long id;
    @Column(name = "name")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Department))
            return false;
        return ((Department) o).name.equals(this.name);
    }
}
