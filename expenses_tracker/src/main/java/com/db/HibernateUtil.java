package com.db;
import com.entity.Expense;
import com.entity.User;


import java.util.Properties;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;




public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL,
                        "jdbc:mysql://localhost:3306/expenses_tracker_db?useSSL=false&serverTimezone=UTC");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "root");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                properties.put(Environment.HBM2DDL_AUTO, "update"); // create/update table automatically
                properties.put(Environment.SHOW_SQL, "true"); // show SQL in console
                properties.put(Environment.USE_SECOND_LEVEL_CACHE, "root");
                properties.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.encache.internal.EncacheRegionFactory");

                configuration.setProperties(properties);

                // Add your entity class
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Expense.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Throwable ex) {
            	System.out.println("SessionFactory creation failed");
                ex.printStackTrace();
            }
        }

        return sessionFactory;
    }
}