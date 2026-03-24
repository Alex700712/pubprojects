package main.entities;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.FIND_ALL_CLIENTS;

public class ServiceEntity
{
    private int serviceId;

    private String name;

    private float cost;

    //private List<Client> clients;
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

//    public void cutRelation()
//    {
//        this.clients = null;
//    }
//
//    public void findRelations()
//    {
//        Type clientListType = new TypeToken<ArrayList<Client>>() {}.getType();
//        List<Client> clients = (new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_CLIENTS), clientListType));
//        for(Client client: clients) {
//            ServiceEntity service = client.getService();
//            if(service.getServiceId() != this.serviceId)
//                clients.remove(client);
//        }
//        this.setClients(clients);
//    }
}

