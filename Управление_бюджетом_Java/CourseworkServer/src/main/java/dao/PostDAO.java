package dao;

import connections.HibernateSessionFactory;
import entities.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PostDAO implements DAO<Post>
{

    @Override
    public void save(Post obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Post obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Post obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Post findById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Post post = session.get(Post.class, id);
        session.close();
        return post;
    }

    @Override
    public List<Post> findAll() {
        Session session =   HibernateSessionFactory.getSessionFactory().openSession();
        List<Post> posts = session.createQuery("From Post", Post.class).list();
        session.close();
        return posts;
    }
}
