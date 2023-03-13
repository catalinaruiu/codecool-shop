package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = {"/cart"})
public class CartServlet extends javax.servlet.http.HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LoadService load = LoadService.getInstance(logger);
        List<ProductDTO> productsInCart = new ArrayList<>();

        HttpSession session = req.getSession(false);
        if (session != null) {
            int userid = (Integer) session.getAttribute("userid");
            productsInCart = load.getAllProductDTOsByUser(userid);
        }
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cartProducts", productsInCart);

        try {
            engine.process("product/cart.html", context, resp.getWriter());
        } catch (IOException e) {
            logger.error("Error by trying to write servlet response", e);
            throw new RuntimeException(e);
        }
    }
}
