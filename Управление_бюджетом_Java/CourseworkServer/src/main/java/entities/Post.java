package entities;

import jakarta.persistence.*;
import service.EmployeeService;

import java.util.List;

@Entity
@Table(name = "post")
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private int postId;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "priority")
    private int priority;
    @Column(name = "salary")
    private float salary;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
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

//    public void cutRelation()
//    {
//        this.setEmployees(null);
//    }
//
//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }
//
//    public void findRelations()
//    {
//        List<Employee> employees = new EmployeeService().findAllEntities();
//        employees.removeIf(employee -> employee.getPosts().getPostId() != this.getPostId());
//        this.setEmployees(employees);
//    }
}
