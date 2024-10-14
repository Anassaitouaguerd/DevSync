package com.demo.Servlets;

import com.demo.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Auth", value = "/login")
public class Auth  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the user object from the request (it could be passed after authentication)
        User user = (User) request.getAttribute("user");

        // Check if user is not null before adding it to the session
        if (user != null) {
            // Get the session and store the user object
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect to display all users
            response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
        } else {
            // Handle the case where the user is null, maybe redirect to an error page or login page
            response.sendRedirect(request.getContextPath() + "/login-error");
        }
    }
}
