package com.codecool.shop.service.product;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;
import java.util.Optional;

public class ProductDbService implements ProductService {
    private final ProductDao productDaoJdbc;
    private final ProductCategoryDao productCategoryDaoJdbc;
    private final SupplierDao supplierDaoJdbc;
    private final CartDao cartDao;

    public ProductDbService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao, CartDao cartDao) {
        this.productDaoJdbc = productDao;
        this.productCategoryDaoJdbc = productCategoryDao;
        this.supplierDaoJdbc = supplierDao;
        this.cartDao = cartDao;
    }

    public ProductCategory getProductCategory(int categoryId) {
        return productCategoryDaoJdbc.find(categoryId);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        ProductCategory category = getProductCategory(categoryId);
        return productDaoJdbc.getBy(category);
    }

    public List<Product> getProductsBySupplier(int supplierId) {
        Supplier supplier = supplierDaoJdbc.find(supplierId);
        return productDaoJdbc.getBy(supplier);
    }

    public Product getProductById(int productId) {
        return productDaoJdbc.find(productId);
    }

    public void addToCart(ProductDTO productDTO){
        cartDao.addToCart(productDTO);
    }

    public void removeFromCart(ProductDTO productDTO) {
        cartDao.removeFromCart(productDTO);
    }

    public Optional<ProductDTO> getProductDTOById(String id) {
        return cartDao.getProductDTOById(id);
    }

    public void updateOrderId(int orderId, int userId){
        cartDao.updateOrderId(orderId, userId);
    }
}
