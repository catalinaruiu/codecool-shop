package com.codecool.shop.controller.APIServlets;

import com.codecool.shop.controller.LoadService;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {"/api/cart"})
public class CartApiServlet extends javax.servlet.http.HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartApiServlet.class);
    private final ServletService service = new ServletService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String query = request.getQueryString();
        Product productForCart;
        LoadService load = LoadService.getInstance(logger);

        HttpSession session = request.getSession(false);
        int userId = session != null ? (Integer) session.getAttribute("userid") : 0;

        if (query == null) {
            List<ProductDTO> productsDTO = load.getAllProductDTOsByUser(userId);
            service.sendJsonResponse(productsDTO, response);
        } else {
            String[] split = query.split("=");
            String key = split[0];
            String value = split[1];
            productForCart = service.getProductByIdByDao(Integer.parseInt(value));
            ProductDTO productDTO = service.getProductDto(productForCart);
            productDTO.setUserId(String.valueOf(userId));
            if (key.equals("addId")) {
                service.addToCartByDao(productDTO);
            } else if (key.equals("removeId")) {
                Optional<ProductDTO> productDTOOptional = load.getProductDTOByIdByUser(value, userId);
                productDTOOptional.ifPresent(dto -> dto.setUserId(String.valueOf(userId)));
                productDTOOptional.ifPresent(service::removeFromCartByDao);
            }
            List<ProductDTO> productsDTO = load.getAllProductDTOsByUser(userId);
            service.sendJsonResponse(productsDTO, response);
        }
    }
}

