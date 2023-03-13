package com.codecool.shop.service;

import com.codecool.shop.controller.LoadService;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.product.ProductDTOService;
import com.codecool.shop.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
public class ServletService {

    private static final Logger logger = LoggerFactory.getLogger(ServletService.class);
    private final ProductDTOService productDTOService = new ProductDTOService();

    private final ProductService productService;

    public ServletService() {
        LoadService load = LoadService.getInstance(logger);
        productService = load.getProductService();
    }

    public List<Product> getProductsBySupplierByDao(int id) {
        return productService.getProductsBySupplier(id);
    }

    public List<Product> getProductsByCategoryByDao(int id) {
        return productService.getProductsByCategory(id);
    }

    public Product getProductByIdByDao(int id) {
        return productService.getProductById(id);
    }

    public void addToCartByDao(ProductDTO productDTO) {
        productService.addToCart(productDTO);
    }

    public void removeFromCartByDao(ProductDTO productDTO) {
        productService.removeFromCart(productDTO);
    }

    public void updateOrderId(int orderId, int userId) {
        productService.updateOrderId(orderId, userId);
    }

    public List<ProductDTO> getProductsDto(List<Product> products) {
        return productDTOService.getProducts(products);
    }

    public ProductDTO getProductDto(Product product) {
        return productDTOService.getProduct(product);
    }

    public void sendJsonResponse(List<ProductDTO> productsDTO, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String jsonResponse = objectMapper.writeValueAsString(productsDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
            out.flush();
        } catch (IOException e) {
            logger.error("Cannot write Json Response", e);
            throw new RuntimeException(e);
        }
    }

}