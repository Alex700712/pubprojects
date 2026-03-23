package Database.DAO;

import java.util.List;

public interface DAO<T>
{
    void add (T obj);
    void update(T obj);
    void delete(T obj);
    T findEntityById(int id);
    List<T> findAllEntities();
}
