package com.biblio.app.config;

import com.biblio.app.entities.AuteurEntity;
import com.biblio.app.entities.EmpruntEntity;
import com.biblio.app.entities.LivreEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                PropertiesReader reader = new PropertiesReader("database.properties");
                Properties settings = new Properties();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", reader.getProperty("db.url"));
                settings.put("hibernate.connection.username", reader.getProperty("db.username"));
                settings.put("hibernate.connection.password", reader.getProperty("db.password"));
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                settings.put("hibernate.hbm2ddl.auto", "update");
                settings.put("hibernate.show_sql", "true");

                Configuration configuration = new Configuration();
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(AuteurEntity.class);
                configuration.addAnnotatedClass(LivreEntity.class);
                configuration.addAnnotatedClass(EmpruntEntity.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur Hibernate: " + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
