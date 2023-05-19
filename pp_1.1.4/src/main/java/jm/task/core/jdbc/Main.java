package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Коля", "Сашин", (byte) 20);
        userService.saveUser("Петя", "Вовин", (byte) 25);
        userService.saveUser("Саша", "Петин", (byte) 31);
        userService.saveUser("Вова", "Васин", (byte) 38);

        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
