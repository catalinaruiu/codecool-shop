package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.jdbc.*;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.product.ProductDbService;
import com.codecool.shop.service.product.ProductMemService;
import com.codecool.shop.service.product.ProductService;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class LoadService {
    private static LoadService instance = null;
    private final String memory = "memory";
    private final String jdbc = "jdbc";
    private final String dao;

    private LoadService(Logger logger) {
        dao = getDao(logger);
    }

    public static LoadService getInstance(Logger logger) {
        if (instance == null) {
            instance = new LoadService(logger);
        }
        return instance;
    }

    public String getDao(Logger logger) {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/connection.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);

            return (String) properties.get("dao");

        } catch (FileNotFoundException e) {
            logger.error("Could not find file", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Could not read from file stream", e);
            throw new RuntimeException(e);
        }
    }

    public ProductService getProductService() {
        ProductService service = null;

        if (dao.equals(memory)) {
            ProductDao productDataStore = ProductDaoMem.getInstance();
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
            CartDao cartDaoMem = CartDaoMem.getInstance();
            service = new ProductMemService(productDataStore, productCategoryDataStore, supplierDataStore, cartDaoMem);

        } else if (dao.equals(jdbc)) {

            ShopDataManager dbManager = ShopDataManager.getInstance();
            DataSource dataSource = dbManager.connect();
            ProductDaoJdbc productDaoJdbc = ProductDaoJdbc.getInstance(dataSource);
            ProductCategoryDaoJdbc categoryDaoJdbc = ProductCategoryDaoJdbc.getInstance(dataSource);
            SupplierDaoJdbc supplierDaoJdbc = SupplierDaoJdbc.getInstance(dataSource);
            CartDao orderDao = CartDaoJdbc.getInstance(dataSource);
            service = new ProductDbService(productDaoJdbc, categoryDaoJdbc, supplierDaoJdbc, orderDao);
        }
        return service;
    }

    public List<ProductCategory> getCategories() {
        List<ProductCategory> categories = null;
        if (dao.equals(memory)) {
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

            categories = productCategoryDataStore.getAll();

        } else if (dao.equals(jdbc)) {
            ShopDataManager dbManager = ShopDataManager.getInstance();
            DataSource dataSource = dbManager.connect();
            ProductCategoryDaoJdbc categoryDaoJdbc = ProductCategoryDaoJdbc.getInstance(dataSource);

            categories = categoryDaoJdbc.getAll();

        }
        return categories;
    }

    public List<Product> getAllProducts() {
        List<Product> products = null;
        if (dao.equals(memory)) {
            ProductDao productDataStore = ProductDaoMem.getInstance();

            products = productDataStore.getAll();

        } else if (dao.equals(jdbc)) {
            ShopDataManager dbManager = ShopDataManager.getInstance();
            DataSource dataSource = dbManager.connect();
            ProductDaoJdbc productDaoJdbc = ProductDaoJdbc.getInstance(dataSource);

            products = productDaoJdbc.getAll();

        }
        return products;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = null;
        if (dao.equals(memory)) {
            SupplierDao supplierDao = SupplierDaoMem.getInstance();

            suppliers = supplierDao.getAll();

        } else if (dao.equals(jdbc)) {
            ShopDataManager dbManager = ShopDataManager.getInstance();
            DataSource dataSource = dbManager.connect();
            SupplierDaoJdbc supplierDaoJdbc = SupplierDaoJdbc.getInstance(dataSource);

            suppliers = supplierDaoJdbc.getAll();

        }
        return suppliers;
    }

    public List<ProductDTO> getAllProductDTOsByUser(int userId) {

        ShopDataManager dbManager = ShopDataManager.getInstance();
        DataSource dataSource = dbManager.connect();
        CartDaoJdbc cartDaoJdbc = CartDaoJdbc.getInstance(dataSource);

        return cartDaoJdbc.getProductsDTOByUser(userId);
    }

    public Optional<ProductDTO> getProductDTOByIdByUser(String productId, int userId) {

        ShopDataManager dbManager = ShopDataManager.getInstance();
        DataSource dataSource = dbManager.connect();
        CartDaoJdbc cartDaoJdbc = CartDaoJdbc.getInstance(dataSource);

        return cartDaoJdbc.getProductDTOByIdByUser(productId, userId);
    }
    public void deleteCartContent(int userId){
        ShopDataManager dbManager = ShopDataManager.getInstance();
        DataSource dataSource = dbManager.connect();
        CartDaoJdbc cartDaoJdbc = CartDaoJdbc.getInstance(dataSource);
        cartDaoJdbc.clearCart(userId);
    }
}

