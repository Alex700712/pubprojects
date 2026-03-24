package entities;

import jakarta.persistence.*;
import service.EmployeeService;

import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "login", nullable = false, length = 45)
    private String login;
    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private
    Employee employee;

    public User() {}
    public User(String login, String password, String role)
    {

        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getUserId() { return this.userId; }

    public String getLogin() { return this.login; }

    public String getPassword() { return this.password; }

    public void setUserId(int id) { this.userId = id; }

    public void setLogin(String login) { this.login = login; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void cutRelation()
    {
        this.employee = null;
    }

    public void findRelation()
    {
        List<Employee> employees = new EmployeeService().findAllEntities();
        for(Employee employee: employees)
        {
            if(employee.getUser().getUserId() == this.getUserId()) {
                this.setEmployee(employee);
                break;
            }
        }
    }
}
