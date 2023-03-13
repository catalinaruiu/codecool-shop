package com.codecool.shop.controller.APIServlets;

import com.codecool.shop.controller.LoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/clear-cart"})
public class ClearCart  extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartApiServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        int userId = session != null ? (Integer) session.getAttribute("userid") : 0;
        LoadService load = LoadService.getInstance(logger);
        load.deleteCartContent(userId);

    }
}
