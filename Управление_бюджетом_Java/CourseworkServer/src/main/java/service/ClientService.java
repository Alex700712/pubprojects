package service;

import dao.DAO;
import dao.ClientDAO;
import entities.Client;
import java.util.List;

public class ClientService implements Service<Client>
{
    public DAO<Client> daoService = new ClientDAO();

    @Override
    public void saveEntity(Client entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(Client entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(Client entity) {
        daoService.delete(entity);
    }

    @Override
    public Client findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<Client> findAllEntities() {
        return daoService.findAll();
    }
}
