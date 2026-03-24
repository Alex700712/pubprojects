package service;

import dao.*;
import entities.Employee;
import java.util.List;

public class EmployeeService implements Service<Employee>
{
    public DAO<Employee> daoService = new EmployeeDAO();

    @Override
    public void saveEntity(Employee entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(Employee entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(Employee entity) {
        daoService.delete(entity);
    }

    @Override
    public Employee findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<Employee> findAllEntities() {
        return daoService.findAll();
    }
}
