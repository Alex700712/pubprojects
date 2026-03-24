package dao;

import connections.HibernateSessionFactory;
import entities.Finance;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FinanceDAO implements DAO<Finance>
{

    @Override
    public void save(Finance obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Finance obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Finance obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Finance findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Finance finance = session.get(Finance.class, id);
        session.close();
        return finance;
    }

    @Override
    public List<Finance> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<Finance> finances = session.createQuery("From Finance", Finance.class).list();
        session.close();
        return finances;
    }
}

