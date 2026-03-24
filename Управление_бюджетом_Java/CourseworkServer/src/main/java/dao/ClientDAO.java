package dao;

import connections.HibernateSessionFactory;
import entities.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO implements DAO<Client>{

    @Override
    public void save(Client obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Client obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Client obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Client findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public List<Client> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<Client> clients = session.createQuery("From Client", Client.class).list();
        session.close();
        return clients;
    }
}
