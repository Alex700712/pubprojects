package Database.Service;

import java.util.List;

public interface Service<T> {
    void addEntity(T entity);
    void updateEntity(T entity);
    void deleteEntity(T entity);
    T findEntityById(int id);
    List<T> findAllEntities();
}
