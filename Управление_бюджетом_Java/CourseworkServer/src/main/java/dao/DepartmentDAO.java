package dao;

import connections.HibernateSessionFactory;
import entities.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentDAO implements DAO<Department>
{

    @Override
    public void save(Department obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Department obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Department obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Department findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Department department = session.get(Department.class, id);
        session.close();
        return department;
    }

    @Override
    public List<Department> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<Department> departments = session.createQuery("From Department", Department.class).list();
        session.close();
        return departments;
    }
}

