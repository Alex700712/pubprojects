package Database.DAO;

import Database.Entities.*;
import Database.HibernateSessionFactory;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class KeyDAO implements DAO<Key>{

    public void add (Key obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(obj);
        transaction.commit();
        session.close();
    }
    public void update(Key obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(obj);
        transaction.commit();
        session.close();
    }
    public void delete(Key obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public Key findEntityById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        return session.get(Key.class, id);
    }

    @Override
    public List<Key> findAllEntities() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Key", Key.class).list();

    }

    public List<Key> getSortedByOrg()
    {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Key ORDER BY organization", Key.class).list();
    }

    public Key findByKey(UUID unique_key)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("from Key where key = :unique_key", Key.class);
        query.setParameter("unique_key", unique_key);
        Key key = (Key)query.getSingleResult();
        session.close();
        return key;
    }
}
