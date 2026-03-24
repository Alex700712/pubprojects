package main.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class Department {

    private int departmentId;
    private String name;
    private int peopleCount;

//    private List<Finance> finances;

//    private List<Employee> employees;

//    private List<Client> clients;

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

//    public void cutRelations()
//    {
//        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
//        List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
//                .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
//        for (Employee employee: employees)
//            if(employee.getDepartments().getDepartmentId() == this.getDepartmentId())
//                employee.setDepartments(null);
//
//        Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
//        List<Finance> finances = (new Gson().fromJson(new GetService<>(Finance.class)
//                .GetEntities(FIND_ALL_FINANCES), financeListType));
//        for (Finance finance: finances)
//            if(finance.getDepartment().getDepartmentId() == this.getDepartmentId())
//                finance.setDepartment(null);
//
//        Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
//        List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_CLIENTS), clientListType));
//        for (Client client: clients)
//            if(client.getDepartment().getDepartmentId() == this.getDepartmentId())
//                client.setDepartment(null);
//    }
//
//    public void findRelations()
//    {
//        Type employeeListType = new TypeToken<ArrayList<Employee>>() {}.getType();
//        List<Employee> employees = (new Gson().fromJson(new GetService<>(Employee.class)
//                .GetEntities(FIND_ALL_EMPLOYEES), employeeListType));
//        employees.removeIf(employee -> employee.getDepartments().getDepartmentId() != this.getDepartmentId());
//        this.setEmployees(employees);
//
//        Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
//        List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_CLIENTS), clientListType));
//        clients.removeIf(client -> client.getDepartment().getDepartmentId() != this.getDepartmentId());
//        this.setClients(clients);
//
//        Type financeListType = new TypeToken<ArrayList<Finance>>() {}.getType();
//        List<Finance> financeList = (new Gson().fromJson(new GetService<>(Finance.class)
//                .GetEntities(FIND_ALL_FINANCES), financeListType));
//        financeList.removeIf(finance -> finance.getDepartment().getDepartmentId() != this.getDepartmentId());
//        this.setFinances(financeList);
//    }
//
//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
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
//    public List<Finance> getFinances() {
//        return finances;
//    }
//
//    public void setFinances(List<Finance> finances) {
//        this.finances = finances;
//    }
}

