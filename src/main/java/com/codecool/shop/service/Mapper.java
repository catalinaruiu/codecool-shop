package com.codecool.shop.service;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
public class Mapper {
    public ProductDTO toDto(Product product) {
        String id = String.valueOf(product.getId());
        String name = product.getName();
        String defaultPrice = String.valueOf(product.getDefaultPrice());
        String description = product.getDescription();
        String productCategory = product.getProductCategory().getName();
        String supplier = product.getSupplier().getName();

        return new ProductDTO(id, name, defaultPrice, description, productCategory, supplier);
    }
}
