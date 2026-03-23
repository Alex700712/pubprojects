package Database;

import Database.Entities.*;
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
                configuration.addAnnotatedClass(Organization.class);
                configuration.addAnnotatedClass(Key.class);
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
