package com.demo.service;

import com.demo.entity.Token;
import com.demo.entity.User;
import com.demo.repository.TokenRepository;
import com.demo.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public class TokenService {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;

    public TokenService() {
        this.tokenRepository = new TokenRepository();
        this.userRepository = new UserRepository();
    }

    private String generateToken(){
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public void createToken(Long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            for (int i = 0; i < 2; i++) {
                String uniqueToken = generateToken();
                Token token = new Token(uniqueToken, user, "remplacer");
                tokenRepository.createToken(token);
            }
            String uniqueToken = generateToken();
            Token token = new Token(uniqueToken, user, "suppression");
            tokenRepository.createToken(token);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public boolean useToken(Long userId, String tokenType) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Token token = tokenRepository.findActiveTokenByUserAndType(user, tokenType);
        if (token == null) {
            return false;
        }
        token.setStatus("used");
        tokenRepository.updateToken(token);
        return true;
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