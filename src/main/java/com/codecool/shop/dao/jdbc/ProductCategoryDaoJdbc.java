package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryDaoJdbc.class);
    private static ProductCategoryDaoJdbc instance = null;
    private final DataSource dataSource;

    private ProductCategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public static ProductCategoryDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc(dataSource);
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO category (name, department, description) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            category.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductCategory find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM category WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            return getCategory(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private ProductCategory getCategory(int id, ResultSet result) throws SQLException {
        String name = result.getString("name");
        String department = result.getString("department");
        String description = result.getString("description");

        ProductCategory category = new ProductCategory(name, department, description);
        category.setId(id);

        return category;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM category WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM category;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<ProductCategory> categories = new ArrayList<>();

            while (result.next()) {
                int categoryId = result.getInt(1);
                ProductCategory productCategory = getCategory(categoryId, result);
                categories.add(productCategory);
            }

            return categories;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }
}
