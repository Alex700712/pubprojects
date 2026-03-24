package dao;

import connections.HibernateSessionFactory;
import entities.Employee;
import entities.Finance;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAO implements DAO<Employee>
{

    @Override
    public void save(Employee obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Employee obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Employee obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Employee findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        session.close();
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("From Employee", Employee.class).list();
        session.close();
        return employees;
    }
}

