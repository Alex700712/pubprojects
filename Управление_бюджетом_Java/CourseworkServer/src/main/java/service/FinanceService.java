package service;

import dao.*;
import entities.Finance;
import java.util.List;

public class FinanceService implements Service<Finance>
{
    public DAO<Finance> daoService = new FinanceDAO();

    @Override
    public void saveEntity(Finance entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(Finance entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(Finance entity) {
        daoService.delete(entity);
    }

    @Override
    public Finance findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<Finance> findAllEntities() {
        return daoService.findAll();
    }
}
