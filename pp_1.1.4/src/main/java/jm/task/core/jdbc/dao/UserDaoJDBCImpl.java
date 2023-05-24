package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private final static Connection con = Util.getConnection();


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement pS = con.prepareStatement("CREATE TABLE IF NOT EXISTS Users (\n" +
                    "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "`name` VARCHAR(45) NOT NULL,\n" +
                    "`lastname` VARCHAR(45) NOT NULL,\n" +
                    "`age` INT NOT NULL,\n" +
                    "PRIMARY KEY (`id`));")) {
            pS.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement pS = con.prepareStatement("DROP TABLE IF EXISTS Users")) {
            pS.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pS = con.prepareStatement("INSERT INTO Users (name, lastname, age) VALUES " +
                    "(?, ?, ?);")) {
            pS.setString(1, name);
            pS.setString(2, lastName);
            pS.setInt(3, age);
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement pS = con.prepareStatement("DELETE FROM Users WHERE id=?" + id + ";")) {
            pS.setLong(1, id);
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try(PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement pS = con.prepareStatement("TRUNCATE TABLE Users;")){
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
