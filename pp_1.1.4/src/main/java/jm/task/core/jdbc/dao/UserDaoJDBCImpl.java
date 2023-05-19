package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final static Connection con = Util.getConnection();

    private void createQuery(String query) {
        try (PreparedStatement pS = con.prepareStatement(super.toString())) {
            pS.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        createQuery("CREATE TABLE Users (\n" +
                "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "`name` VARCHAR(45) NOT NULL,\n" +
                "`lastname` VARCHAR(45) NOT NULL,\n" +
                "`age` INT NOT NULL,\n" +
                "PRIMARY KEY (`id`));");
    }

    public void dropUsersTable() {
        createQuery("DROP TABLE IF EXISTS Users");
    }

    public void saveUser(String name, String lastName, byte age) {
        createQuery("INSERT INTO Users (name, lastname, age) VALUES " +
                "('" + name + "', '" + lastName + "', " + age + ");");

    }

    public void removeUserById(long id) {
        createQuery("DELETE FROM Users WHERE id=" + id + ";");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try(Statement statement = Util.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(sql);
            while(res.next()) {
                list.add(new User(res.getString("name"),
                        res.getString("lastname"), res.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        createQuery("TRUNCATE TABLE Users;");
    }
}
