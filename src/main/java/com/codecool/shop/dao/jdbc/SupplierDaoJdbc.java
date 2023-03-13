package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {
    private static final Logger logger = LoggerFactory.getLogger(SupplierDaoJdbc.class);
    private static SupplierDaoJdbc instance = null;
    private final DataSource dataSource;

    private SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public static SupplierDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new SupplierDaoJdbc(dataSource);
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO supplier (name, description) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            supplier.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM supplier WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            return getSupplier(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private Supplier getSupplier(int id, ResultSet result) throws SQLException {
        String name = result.getString("name");
        String description = result.getString("description");

        Supplier supplier = new Supplier(name, description);
        supplier.setId(id);

        return supplier;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM supplier WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM supplier;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<Supplier> suppliers = new ArrayList<>();

            while (result.next()) {
                int supplierId = result.getInt(1);
                Supplier supplier = getSupplier(supplierId, result);
                suppliers.add(supplier);
            }
            return suppliers;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }
}
