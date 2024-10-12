package com.demo.service;

import com.demo.entity.Token;
import com.demo.entity.User;
import com.demo.repository.TokenRepository;
import com.demo.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public class TokenService {
    private String generateToken(){
        return RandomStringUtils.randomAlphanumeric(32);
    }
    public void createToken(Long userId) {
        Optional<User> userOptional = new UserRepository().findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            for (int i = 0; i < 2; i++) {
                String uniqueToken = generateToken();
                Token token = new Token(uniqueToken, user, "remplacer");
                new TokenRepository().createToken(token);
            }
            String uniqueToken = generateToken();
            Token token = new Token(uniqueToken, user, "suppression");
            new TokenRepository().createToken(token);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public void deleteToken() {
        // Delete a token
    }

    public void updateToken() {
        // Update a token
    }

    public void findToken() {
        // Find a token
    }
}
