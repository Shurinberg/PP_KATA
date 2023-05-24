package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/pp1";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";


    static {
        try {
           connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // ниже для Hibernate, не закомментировал, красное подчеркивание раздражает)
    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
                properties.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("hibernate.connection.url", URL);
                properties.setProperty("hibernate.connection.username", LOGIN);
                properties.setProperty("hibernate.connection.password", PASSWORD);
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                properties.setProperty("hibernate.use_sql_comments", "true");
                properties.setProperty("hibernate.show_sql", "true");
                properties.setProperty("hibernate.format_sql", "true");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
        configuration.setProperties(properties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    public static Connection getConnection() {
        return connection;
    }
}
