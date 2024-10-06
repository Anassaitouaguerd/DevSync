package com.demo.route;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "user", value = "/user/*")
public class UserRoute extends HttpServlet {
    private UserService userService;
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
        userRepository = new UserRepository();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        try {
            switch (pathInfo) {
                case "/display-All-Users":
                    List<User> users = userService.displayUsers();
                    request.setAttribute("usersList", users);
                    request.getRequestDispatcher("/GestionUsers.jsp").forward(request, response);
                    break;
                case "/add":
                    request.getRequestDispatcher("/AddUser.jsp").forward(request, response);
                    break;
                case "/update-user":
                    String email = request.getParameter("email");
                    Map<String, Object> user = new UserRepository().findUserByEmail(email.trim());
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/UpdateUser.jsp").forward(request, response);
                    break;
                case "/delete-user":
                    String emailToDelete = request.getParameter("email");
                    userService.deleteUser(emailToDelete);
                    response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception
            getServletContext().log("Error in UserRoute", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        switch (pathInfo) {
            case "/new":
                userService.createUser(request);
                response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
                break;
            case "/update":
                String method = request.getParameter("_method");
                if ("PUT".equalsIgnoreCase(method)) {
                    doPut(request, response);
                }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        try {
            Optional<User> userOptional = userRepository.findUserById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                updateUserFromRequest(user, request);
                userRepository.update(user);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating user");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/user/display-All-Users");

    }

    private void updateUserFromRequest(User user, HttpServletRequest request) {
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setAdresse(request.getParameter("adresse"));
        user.setManager(Boolean.parseBoolean(request.getParameter("manager")));
    }
}