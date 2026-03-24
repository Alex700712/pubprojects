package connections;

import entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Post.class);
                configuration.addAnnotatedClass(Department.class);
                configuration.addAnnotatedClass(Finance.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(ServiceEntity.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
            }
        }
        return sessionFactory;
    }
}
