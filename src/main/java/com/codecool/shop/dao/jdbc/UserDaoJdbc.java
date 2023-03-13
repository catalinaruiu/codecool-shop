package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.service.PasswordService;
import com.codecool.shop.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);
    private static UserDaoJdbc instance = null;
    private final DataSource dataSource;

    private UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new UserDaoJdbc(dataSource);
        }
        return instance;
    }

    @Override
    public void add(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO " +
                    "users (name, email, password) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, PasswordService.hashPassword(user.getPassword()));

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            return getUser(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private User getUser(int id, ResultSet result) throws SQLException {
        String name = result.getString("name");
        String email = result.getString("email");
        String password = result.getString("password");

        User user = new User(name, email, password);
        user.setId(id);

        return user;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM users WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (result.next()) {
                int userId = result.getInt(1);
                User user = getUser(userId, result);
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }
}
