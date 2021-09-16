package com.example.pwc.Services;

import com.example.pwc.Models.Department;
import java.util.List;

public interface DepartmentManagementService {
    public List<Department> getAll();
    public Department create(Department d);
    public boolean delete(Department d);
    public Department getByName(String dName);
}
