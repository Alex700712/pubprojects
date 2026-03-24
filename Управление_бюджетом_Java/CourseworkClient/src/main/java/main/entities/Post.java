package main.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class Post
{
    private int postId;

    private String name;
    private int priority;
    private float salary;
//    private List<Employee> employees;

    public Post() {}

    public Post(String name, int priority, float salary)
    {
        this.name = name;
        this.priority = priority;
        this.salary = salary;
    }

    public int getPostId() { return postId;}

    public String getName() { return name; }

    public int getPriority() { return priority; }

    public float getSalary() { return salary; }

    public void setPostId(int postId) { this.postId = postId; }

    public void setName(String name) { this.name = name; }

    public void setPriority(int priority) { this.priority = priority; }

    public void setSalary(float salary) { this.salary = salary; }

//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }

//    public void cutRelation() {
//        this.setEmployees(null);
//    }
//
//    public void findRelations()
//    {
//        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
//        List<Employee> employees = (new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
//        employees.removeIf(employee -> employee.getPosts().getPostId() != this.getPostId());
//        this.setEmployees(employees);
//    }
}

