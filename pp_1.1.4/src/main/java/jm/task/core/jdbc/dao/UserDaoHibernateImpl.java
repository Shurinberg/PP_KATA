package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = new Util().getSessionFactory();
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users" +
                    "(id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL," +
                    "age INT NOT NULL," +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.find(User.class, id));
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User order by name").list();
            transaction.commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
            }
        }
    }
