package com.codecool.shop.controller.user;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.RegistrationService;
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

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends javax.servlet.http.HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ServletService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        try {
            engine.process("user/login.html", context, resp.getWriter());
        } catch (IOException e) {
            logger.error("Error by trying to write servlet response", new Throwable(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String error = "";
        if (RegistrationService.emailMatchesPassword(email, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("userid", RegistrationService.getIdForUserInSession(email));
            session.setAttribute("userName", RegistrationService.getNameFromUserEmail(email));
            resp.sendRedirect("/");
        } else {
            if (!RegistrationService.emailAlreadyExists(email)) {
                error = "User doesn't exist";
            } else if (!RegistrationService.emailMatchesPassword(email, password)) {
                error = "Wrong username or password!";
            }
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("error", error);
            engine.process("user/login.html", context, resp.getWriter());
        }
    }
}
