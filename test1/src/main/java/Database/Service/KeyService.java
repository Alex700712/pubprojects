package Database.Service;

import Database.DAO.KeyDAO;
import Database.Entities.Key;

import java.util.List;
import java.util.UUID;

public class KeyService implements Service<Key> {

    public KeyDAO keyDao = new KeyDAO();

    @Override
    public void addEntity(Key entity) { keyDao.add(entity); }

    @Override
    public void updateEntity(Key entity) { keyDao.update(entity); }

    @Override
    public void deleteEntity(Key entity) { keyDao.delete(entity); }

    @Override
    public Key findEntityById(int id) { return keyDao.findEntityById(id); }

    @Override
    public List<Key> findAllEntities() { return keyDao.findAllEntities(); }



    public Key findEntity(UUID key) { return keyDao.findByKey(key); }

    public List<Key> GetSortedEntityList() { return keyDao.getSortedByOrg(); }
}
