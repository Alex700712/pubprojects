package entities;

import jakarta.persistence.*;
import service.ClientService;

import java.util.List;


@Entity
@Table(name = "service")
public class ServiceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private int serviceId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private float cost;

//    @OneToMany
//    umn(name = "client_Id", referen@JoinTable(name = "service",
//                    joinColumns = @JoinColcedColumnName = "client_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "service_id"))
//    private List<Client> clients;

    public ServiceEntity() {}

    public ServiceEntity(String name, float cost)
    {
        this.name = name;
        this.cost = cost;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }
//
//    public void cutRelation()
//    {
//        this.clients = null;
//    }
//
//    public void findRelations()
//    {
//        List<Client> clients = new ClientService().findAllEntities();
//        for(Client client: clients) {
//            List<ServiceEntity> services = client.getServices();
//            if(!services.contains(this))
//                clients.remove(client);
//        }
//        this.setClients(clients);
//    }
}
