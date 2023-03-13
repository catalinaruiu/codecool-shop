package com.codecool.shop.service.product;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;
import java.util.Optional;


public class ProductMemService implements ProductService {
    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final SupplierDao supplierDao;

    private final CartDao cartDao;

    public ProductMemService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao, CartDao cartDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
        this.cartDao = cartDao;
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<Product> getProductsBySupplier(int supplierId) {
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier);
    }

    public Product getProductById(int productId){
        return productDao.find(productId);
    }

    public void addToCart(ProductDTO productDTO) {
        cartDao.addToCart(productDTO);
    }

    public void removeFromCart(ProductDTO productDTO) {
        cartDao.removeFromCart(productDTO);
    }

    public Optional<ProductDTO> getProductDTOById(String id) {
        return cartDao.getProductDTOById(id);
    }

    @Override
    public void updateOrderId(int orderId, int userId) {
//        NO IMPLEMENTATION FOR MEMORY
    }
}
