package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDaoMem implements CartDao {
    private final List<ProductDTO> productsDTO = new ArrayList<>();

    private static CartDaoMem instance = null;

    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void addToCart(ProductDTO product){
        productsDTO.add(product);
    }
    @Override
    public void removeFromCart(ProductDTO product){
        productsDTO.remove(product);
    }
    @Override
    public Optional<ProductDTO> getProductDTOById(String id){
        return productsDTO.stream().filter(productDTO -> productDTO.getId().equals(id)).findFirst();
    }
    @Override
    public List<ProductDTO> getProductsDTO() {
        return productsDTO;
    }

    @Override
    public void updateOrderId(int orderId, int userId) {
//        NO IMPLEMENTATION FOR MEMORY
    }
}

