package com.demo.web;

import com.demo.entity.User;
import com.demo.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/login")
public class WebFilterServlets implements Filter {
    public void doFilter(ServletRequest req , ServletResponse res, FilterChain chain) {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = new UserService().findUserByEmail(email);
            if (user == null) {
                req.setAttribute("errorMessage", "User not found");
                ((HttpServletResponse) res).sendRedirect("/?error=User+not+found");
                return;
            }
            if (!user.getPassword().equals(password)) {
                req.setAttribute("errorMessage", "Invalid password");
                ((HttpServletResponse) res).sendRedirect("/?error=Invalid+password");
                return;
            }
            req.setAttribute("user", user);
            chain.doFilter(req, res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
