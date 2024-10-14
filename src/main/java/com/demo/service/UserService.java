package com.demo.service;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


public class UserService {
    private final UserRepository userRepository;
public UserService() {
    this.userRepository = new UserRepository();
    }
    public void createUser(HttpServletRequest request) {
        User user = extractUserFromRequest(request);
        userRepository.create(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);

    }

    public User findUserByEmail(String email) {
        return userRepository.find(email);
    }
    public void deleteUser(String email) {

        userRepository.delete(email);
    }

    public List<User> displayUsers() {
        return userRepository.display();
    }

    private User extractUserFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adresse = request.getParameter("adresse");
        Boolean isManager = request.getParameter("manager") != null;
        return new User(name, email, password, adresse, isManager);
    }
}