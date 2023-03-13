package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private static ProductDaoJdbc instance = null;
    private final DataSource dataSource;

    private ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public static ProductDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductDaoJdbc(dataSource);
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO " +
                    "product (category_id, supplier_id, name, description, defaultprice, currency) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, product.getProductCategory().getId());
            statement.setInt(2, product.getSupplier().getId());
            statement.setString(3, product.getName());
            statement.setString(4, product.getDescription());
            statement.setBigDecimal(5, product.getDefaultPrice());
            statement.setString(6, product.getDefaultCurrency().getSymbol());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM product WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            return getProduct(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private Product getProduct(int id, ResultSet result) throws SQLException {
        int categoryId = result.getInt("category_id");
        int supplierId = result.getInt("supplier_id");
        String name = result.getString("name");
        BigDecimal defaultPrice = result.getBigDecimal("defaultprice");
        String description = result.getString("description");
        String currency = result.getString("currency");

        ProductCategoryDaoJdbc categoryDao = ProductCategoryDaoJdbc.getInstance(dataSource);
        ProductCategory category = categoryDao.find(categoryId);

        SupplierDaoJdbc supplierDao = SupplierDaoJdbc.getInstance(dataSource);
        Supplier supplier = supplierDao.find(supplierId);

        Product product = new Product(name, defaultPrice, currency, description, category, supplier);
        product.setId(id);

        return product;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM product WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM product;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (result.next()) {
                int productId = result.getInt(1);
                Product product = getProduct(productId, result);
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM product WHERE supplier_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, supplier.getId());
            ResultSet result = statement.executeQuery();
            List<Product> productsBySupplier = new ArrayList<>();

            while (result.next()) {
                int productId = result.getInt(1);
                Product product = getProduct(productId, result);
                productsBySupplier.add(product);
            }
            return productsBySupplier;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM product WHERE category_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productCategory.getId());
            ResultSet result = statement.executeQuery();

            List<Product> productsByCategory = new ArrayList<>();

            while (result.next()) {
                int productId = result.getInt(1);
                Product product = getProduct(productId, result);
                productsByCategory.add(product);
            }
            return productsByCategory;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }
}

