package entities;

import jakarta.persistence.*;
import service.ClientService;
import service.DepartmentService;
import service.EmployeeService;
import service.ServiceEntityService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int clientId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    public Client() {}

    public Client(String name)
    {
        this.name = name;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public void cutRelations()
    {
        this.department = null;
        this.service = null;
    }

//    public void findRelations()
//    {
//        List<Department> departments = new DepartmentService().findAllEntities();
//        List<ServiceEntity> services = new ServiceEntityService().findAllEntities();
//        for(ServiceEntity service: services) {
//            List<Client> clients = service.getClients();
//            if(!clients.contains(this))
//                services.remove(service);
//        }
//        this.setServices(services);
//        for(Department dep: departments)
//        {
//            Client client = new ClientService().findEntity(this.getClientId());
//            if(client.getDepartment().getDepartmentId() == dep.getDepartmentId()) {
//                this.department = dep;
//                break;
//            }
//        }
//    }
}
