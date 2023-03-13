package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ProductDTO;
import com.codecool.shop.service.ServletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends javax.servlet.http.HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ServletService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LoadService load = LoadService.getInstance(logger);

        HttpSession session = req.getSession(false);
        int userId = (Integer) session.getAttribute("userid");

        List<ProductDTO> productsInCart = load.getAllProductDTOsByUser(userId);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        BigDecimal totalPrice = calculateTotalPrice(productsInCart);
        context.setVariable("cartProducts", productsInCart);
        context.setVariable("totalPrice", totalPrice);
        try {
            engine.process("product/payment.html", context, resp.getWriter());
        } catch (IOException e) {
            logger.error("Error by trying to write servlet response", new Throwable(e));
        }
    }

    private BigDecimal calculateTotalPrice(List<ProductDTO> productsInCart) {
        BigDecimal sum = new BigDecimal(0);
        for (ProductDTO productDTO : productsInCart) {
            sum = sum.add(BigDecimal.valueOf(Double.parseDouble(productDTO.getDefaultPrice())));
        }
        return sum;
    }
}
