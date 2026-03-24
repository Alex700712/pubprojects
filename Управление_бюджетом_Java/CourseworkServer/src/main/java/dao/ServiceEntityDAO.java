package dao;

import connections.HibernateSessionFactory;
import entities.ServiceEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ServiceEntityDAO implements DAO<ServiceEntity>
{
    @Override
    public void save(ServiceEntity obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(ServiceEntity obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(ServiceEntity obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public ServiceEntity findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        ServiceEntity service = session.get(ServiceEntity.class, id);
        session.close();
        return service;
    }

    @Override
    public List<ServiceEntity> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<ServiceEntity> services = session.createQuery("From ServiceEntity", ServiceEntity.class).list();
        session.close();
        return services;
    }
}
