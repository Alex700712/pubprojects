package Database.DAO;

import Database.Entities.Organization;
import Database.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrganizationDAO implements DAO<Organization> {

    public void add (Organization obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(obj);
        transaction.commit();
        session.close();
    }
    public void update(Organization obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(obj);
        transaction.commit();
        session.close();
    }
    public void delete(Organization obj)
    {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public Organization findEntityById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        return session.get(Organization.class, id);
    }

    @Override
    public List<Organization> findAllEntities() {
        return HibernateSessionFactory.getSessionFactory().openSession().createQuery("from Organization", Organization.class).list();
    }
}
