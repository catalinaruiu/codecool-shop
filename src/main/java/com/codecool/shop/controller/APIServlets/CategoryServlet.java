package com.codecool.shop.controller.APIServlets;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.service.ServletService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = {"/api/products/category"})
public class CategoryServlet extends javax.servlet.http.HttpServlet {
    private final ServletService service = new ServletService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String parameter = req.getParameter("id");
        List<Product> products = service.getProductsByCategoryByDao(Integer.parseInt(parameter));
        List<ProductDTO> productsDTO = service.getProductsDto(products);

        service.sendJsonResponse(productsDTO, resp);

    }
}