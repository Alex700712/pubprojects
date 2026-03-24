package dao;

import connections.HibernateSessionFactory;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO implements DAO<User>
{
    @Override
    public void save(User obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public User findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<User> users = session.createQuery("From User", User.class).list();
        session.close();
        return users;
    }
}
