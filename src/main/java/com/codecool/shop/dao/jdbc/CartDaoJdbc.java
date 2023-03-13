package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.product.ProductDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDaoJdbc implements CartDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private static CartDaoJdbc instance = null;
    private final ProductDTOService productDTOService = new ProductDTOService();
    private final DataSource dataSource;

    private CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CartDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new CartDaoJdbc(dataSource);
        }
        return instance;
    }

    public void addToCart(ProductDTO product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart (product_id, user_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, Integer.parseInt(product.getId()));
            statement.setInt(2, Integer.parseInt(product.getUserId()));

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public void removeFromCart(ProductDTO product) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "WITH row AS (SELECT * FROM cart WHERE product_id = ? AND user_id = ? LIMIT 1)\n" +
                    "DELETE\n" +
                    "FROM cart\n" +
                    "WHERE id IN (SELECT id FROM row);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(product.getId()));
            statement.setInt(2, Integer.parseInt(product.getUserId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<ProductDTO> getProductDTOById(String id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cart WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet result = statement.executeQuery();

            if (!result.next()) return Optional.empty();

            return getProductDTO(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<ProductDTO> getProductDTOByIdByUser(String id, int userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cart WHERE product_id = ? AND user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return Optional.empty();

            return getProductDTO(id, result);

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    private Optional<ProductDTO> getProductDTO(String id, ResultSet result) throws SQLException {
        int productId = result.getInt("product_id");

        ProductDaoJdbc productDao = ProductDaoJdbc.getInstance(dataSource);
        Product product = productDao.find(productId);
        product.setId(Integer.parseInt(id));

        ProductDTO productDTO = productDTOService.getProduct(product);

        return Optional.ofNullable(productDTO);
    }

    public List<ProductDTO> getProductsDTO() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cart;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<ProductDTO> products = new ArrayList<>();

            while (result.next()) {
                int productId = result.getInt(2);
                Optional<ProductDTO> productDTO = getProductDTO(String.valueOf(productId), result);
                productDTO.ifPresent(products::add);
            }
            return products;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public List<ProductDTO> getProductsDTOByUser(int userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cart WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            List<ProductDTO> products = new ArrayList<>();

            while (result.next()) {
                int productId = result.getInt(2);
                Optional<ProductDTO> productDTO = getProductDTO(String.valueOf(productId), result);
                productDTO.ifPresent(products::add);
            }
            return products;

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public void updateOrderId(int orderId, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE cart SET order_id = ? WHERE user_id = ?;";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, orderId);
            statement.setInt(2, userId);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

        } catch (SQLException e) {
            logger.error("Could not connect with database", e);
            throw new RuntimeException(e);
        }
    }

    public void clearCart(int userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.error("Could mot clear cart", e);
            throw new RuntimeException(e);
        }
    }
}
