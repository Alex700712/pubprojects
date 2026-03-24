package service;

import dao.*;
import entities.User;
import java.util.List;

public class UserService implements Service<User>
{
    public DAO<User> daoService = new UserDAO();

    @Override
    public void saveEntity(User entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(User entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(User entity) {
        daoService.delete(entity);
    }

    @Override
    public User findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<User> findAllEntities() {
        return daoService.findAll();
    }


}
