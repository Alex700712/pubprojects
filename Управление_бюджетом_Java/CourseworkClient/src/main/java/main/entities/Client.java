package main.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class Client {
    private int clientId;
    private String name;
    private Department department;
   // private List<ServiceEntity> services;
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

//    public List<ServiceEntity> getServices() { return services; }
//
//    public void setServices(List<ServiceEntity> serviceEntities) {
//        this.services = serviceEntities;
//    }

    public int getDepartmentId() {
        if(this.department != null)
            return this.department.getDepartmentId();
        else return -1;
    }

    public int getServiceId()
    {
        if(this.getService() != null)
            return this.getService().getServiceId();
        else return -1;
    }

//    public List<Integer> getServiceIdList() {
//        List<Integer> serviceIdList = new ArrayList<>();
//        for(ServiceEntity service : services) {
//            if (service != null)
//                serviceIdList.add(service.getServiceId());
//        }
//        if(serviceIdList.isEmpty())
//            serviceIdList.add(-1);
//        return serviceIdList;
//    }
//
//    public void cutRelations()
//    {
//        this.department = null;
//        this.setService(null);
//    }
//
//    public void findRelations() throws IOException {
//        Type departmentListType = new TypeToken<ArrayList<Department>>() {}.getType();
//        List<Department> departments =(new Gson().fromJson(new GetService<>(Department.class)
//                .GetEntities(FIND_ALL_DEPARTMENTS), departmentListType));
//        Type serviceListType = new TypeToken<ArrayList<ServiceEntity>>() {}.getType();
//        List<ServiceEntity> services = (new Gson().fromJson(new GetService<>(ServiceEntity.class)
//            .GetEntities(FIND_ALL_SERVICES), serviceListType));
//
//
//
////        for(ServiceEntity service: services) {
////            List<Client> clients = service.getClients();
////            if(!clients.contains(this))
////                services.remove(service);
////        }
////        this.setServices(services);
//
//
//
//        for(Department dep: departments)
//        {
//            Client client = new GetService<>(Client.class).GetEntityById(FIND_CLIENT, String.valueOf(this.getClientId()));
//            if(client.getDepartment().getDepartmentId() == dep.getDepartmentId()) {
//                this.department = dep;
//                break;
//            }
//        }
//        for(ServiceEntity serviceEntity: services)
//        {
//            Client client = new GetService<>(Client.class).GetEntityById(FIND_CLIENT, String.valueOf(this.getClientId()));
//            if(client.getService().getServiceId() == serviceEntity.getServiceId()) {
//                this.setService(serviceEntity);
//                break;
//            }
//        }
//    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }
}
