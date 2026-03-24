package entities;

import jakarta.persistence.*;
import service.DepartmentService;
import service.PostService;
import service.UserService;

import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int employeeId;
    @Column(name = "name", unique = true, nullable = false, length = 45)
    private  String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {}

    public Employee(String name)
    {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
     }

    public Post getPosts() {
        return post;
    }

    public void setPosts(Post post) {
        this.post = post;
    }

    public Department getDepartments() {
        return department;
    }

    public void setDepartments(Department department) {
        this.department = department;
    }

    public void cutRelation()
    {
        this.user = null;
        this.post = null;
        this.department = null;
    }

    public void findRelation()
    {
        List<User> users = new UserService().findAllEntities();
        for(User user: users)
        {
            if(user.getEmployee().getEmployeeId() == this.getEmployeeId() ||
            user.getEmployee() == null) {
                this.setUser(user);
                break;
            }
        }
//        List<Post> posts = new PostService().findAllEntities();
//        for(Post post : posts)
//        {
//            if(post.getEmployees().contains(this) ||
//                    post.getEmployees() == null) {
//                this.setPosts(post);
//                break;
//            }
//        }
//
//        List<Department> departments = new DepartmentService().findAllEntities();
//        for(Department dep : departments)
//        {
//            if(dep.getEmployees().contains(this) ||
//                    dep.getEmployees() == null) {
//                this.setDepartments(dep);
//                break;
//            }
//        }
    }
}
