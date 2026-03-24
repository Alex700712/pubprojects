package service;

import dao.*;
import entities.Department;
import java.util.List;

public class DepartmentService implements Service<Department>
{
    public DAO<Department> daoService = new DepartmentDAO();

    @Override
    public void saveEntity(Department entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(Department entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(Department entity) {
        daoService.delete(entity);
    }

    @Override
    public Department findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<Department> findAllEntities() {
        return daoService.findAll();
    }
}
