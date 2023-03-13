package com.codecool.shop.dao.jdbc;

import com.codecool.shop.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class OrderDaoJdbc {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private static OrderDaoJdbc instance = null;
    private final DataSource dataSource;

    private OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static OrderDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new OrderDaoJdbc(dataSource);
        }
        return instance;
    }

    public void checkOutOrder(OrderDTO order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO orders (user_id, status, quantity, total) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getUserId());
            statement.setString(2, String.valueOf(order.getStatus()));
            statement.setInt(3, order.getQuantity());
            statement.setBigDecimal(4, order.getTotal());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            order.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<OrderDTO> getOrder(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM orders WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return Optional.empty();

            return getOrderDTO(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private Optional<OrderDTO> getOrderDTO(int id, ResultSet result) throws SQLException {
        int userId = result.getInt("user_id");
        String status = result.getString("status");
        int quantity = result.getInt("quantity");
        BigDecimal total = result.getBigDecimal("total");

        OrderDTO order = new OrderDTO(userId, quantity, total, status);
        order.setId(id);
        return Optional.of(order);
    }

    public void updateStatus(OrderDTO order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE orders SET status = ? WHERE id = ?;";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, String.valueOf(order.getStatus()));
            statement.setInt(2, order.getId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            order.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }
}
