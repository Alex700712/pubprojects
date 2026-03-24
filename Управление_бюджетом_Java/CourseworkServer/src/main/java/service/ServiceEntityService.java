package service;

import dao.DAO;
import dao.ServiceEntityDAO;
import entities.ServiceEntity;

import java.util.List;

public class ServiceEntityService implements Service<ServiceEntity>
{
    public DAO<ServiceEntity> daoService = new ServiceEntityDAO();

    @Override
    public void saveEntity(ServiceEntity entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(ServiceEntity entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(ServiceEntity entity) {
        daoService.delete(entity);
    }

    @Override
    public ServiceEntity findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<ServiceEntity> findAllEntities() {
        return daoService.findAll();
    }
}
