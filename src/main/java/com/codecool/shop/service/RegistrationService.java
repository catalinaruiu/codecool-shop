package com.codecool.shop.service;

import com.codecool.shop.dao.jdbc.ShopDataManager;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import com.codecool.shop.model.user.User;

import javax.sql.DataSource;
import java.util.List;

public class RegistrationService {
    private static final ShopDataManager dbManager = ShopDataManager.getInstance();
    private static final DataSource dataSource = dbManager.connect();
    private static final List<User> users = UserDaoJdbc.getInstance(dataSource).getAll();

    public static boolean userAlreadyExists(User user) {
        List<User> users = UserDaoJdbc.getInstance(dataSource).getAll();
        return users.stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }

    public static boolean emailMatchesPassword(String email, String password) {
        boolean answer = false;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                int userId = user.getId();
                User existingUser = UserDaoJdbc.getInstance(dataSource).find(userId);
                answer = PasswordService.checkPassword(password, existingUser.getPassword());
            }
        }
        return answer;
    }

    public static boolean emailAlreadyExists(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }

    public static int getIdForUserInSession(String email) {
        int userId = 0;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                userId = user.getId();
            }
        }
        return userId;
    }
    public static String getNameFromUserEmail(String email) {
        String name = "";
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                name = user.getName();
            }
        }
        return name;
    }
}
