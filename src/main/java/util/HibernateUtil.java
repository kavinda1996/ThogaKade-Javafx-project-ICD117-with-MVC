package util;

import model.dto.Customer;
import model.dto.Item;
import model.entity.ItemEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = createSessionFactory();


    private static SessionFactory createSessionFactory(){
        Configuration config = new Configuration();
        config.addAnnotatedClass(Item.class);

        config.configure("hibernate.cfg.xml");

        return config.buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
