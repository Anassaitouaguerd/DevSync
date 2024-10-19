package com.demo;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        for (int i = 0; i < 2; i++) {
//            String uniqueToken = RandomStringUtils.randomAlphanumeric(32);
//            System.out.println("Unique ID: " + uniqueToken);
//        }
        Optional<User> userOptional = new UserRepository().findUserById(1L);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User: " + user);
        }
    }
}