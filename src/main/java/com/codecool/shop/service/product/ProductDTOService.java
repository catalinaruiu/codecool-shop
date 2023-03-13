package com.codecool.shop.service.product;

import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.Mapper;

import java.util.List;
import java.util.stream.Collectors;


public class ProductDTOService {
    private final Mapper mapper;

    public ProductDTOService() {
        this.mapper = new Mapper();
    }

    public List<ProductDTO> getProducts(List<Product> products) {
        return products.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public ProductDTO getProduct(Product product){
        return mapper.toDto(product);
    }

}
