package service;

import java.util.List;

public interface Service<T> {

    void saveEntity(T entity);
    void updateEntity(T entity);
    void deleteEntity(T entity);
    T findEntity(int id);
    List<T> findAllEntities();
}
