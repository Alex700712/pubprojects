package entities;

import jakarta.persistence.*;
import service.ClientService;
import service.EmployeeService;
import service.FinanceService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private int departmentId;
    @Column(name = "name", unique = true, length = 45)
    private String name;
    @Column(name = "people_count")
    private int peopleCount;

//    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE)
//    private List<Client> clients;
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE)
//    private List<Finance> finances;
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE)
//    private List<Employee> employees;

    public Department() {}

    public Department(String name, int count)
    {
        this.name = name;
        this.peopleCount = count;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }
//
//    public List<Finance> getFinances() {
//        return finances;
//    }
//
//    public void setFinances(List<Finance> finances) {
//        this.finances = finances;
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
//    public void cutRelations()
//    {
//        this.finances = null;
//        this.employees = null;
//        this.clients = null;
//    }
//
//    public void findRelations()
//    {
//        List<Employee> employees = new EmployeeService().findAllEntities();
//        employees.removeIf(employee -> employee.getDepartments().getDepartmentId() != this.getDepartmentId());
//        this.setEmployees(employees);
//
//        List<Client> clients = new ClientService().findAllEntities();
//        clients.removeIf(client -> client.getDepartment().getDepartmentId() != this.getDepartmentId());
//        this.setClients(clients);
//
//        List<Finance> financeList = new FinanceService().findAllEntities();
//        financeList.removeIf(finance -> finance.getDepartments().getDepartmentId() != this.getDepartmentId());
//        this.setFinances(financeList);
//    }
}
