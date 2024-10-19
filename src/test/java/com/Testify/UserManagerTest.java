package com.Testify;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        System.out.println("UserRepository initialized");
    }

    @Test
    public void testDisplayUsers(){
        User user = new User("John Ait Ouaguerd", "john.doe@aitouaguerd.com", "password123", "123 Main St", false);
        User user2 = new User("John anass", "john.doe@anass.com", "anass2323", "321 Main St", true);

        try {
            System.out.println("Attempting to create first user");
            userRepository.create(user);
            System.out.println("First user created successfully");

            System.out.println("Attempting to create second user");
            userRepository.create(user2);
            System.out.println("Second user created successfully");
        } catch (RuntimeException e) {
            fail("Failed to create users: " + e.getMessage());
        }

        List<User> users = userRepository.display();
        System.out.println("Number of users retrieved: " + users.size());
        users.forEach(u -> System.out.println("User: " + u.getName() + ", Email: " + u.getEmail()));

        assertTrue(users.stream().anyMatch(u -> u.getName().equals("John Ait Ouaguerd")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("John anass")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("john.doe@aitouaguerd.com")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("john.doe@anass.com")));

        deleteUserSafely(user.getEmail());
        deleteUserSafely(user2.getEmail());
    }

    @Test
    public void testCreateSingleUser() {
        User user = new User("John Doe", "john.doe@example.com", "password123", "123 Main St", false);
        try {
            System.out.println("Attempting to create user in testCreateSingleUser");
            userRepository.create(user);
            System.out.println("User created successfully in testCreateSingleUser");
        } catch (RuntimeException e) {
            fail("Failed to create user: " + e.getMessage());
        }

        Map<String, Object> createdUser = userRepository.findUserByEmail("john.doe@example.com");
        assertNotNull(createdUser, "User should exist after creation");
        assertEquals("John Doe", createdUser.get("name"));
    }

    @Test
    public void testUpdateUser() {
        User user = new User("John Ait Ouaguerd", "john.doe@aitouaguerd.com", "password123", "123 Main St", false);
        try {
            userRepository.create(user);
        } catch (RuntimeException e) {
            fail("Failed to create user: " + e.getMessage());
        }

        user.setName("John Doe");
        user.setEmail("hellotest@gmail.com");
        user.setAdresse("2004 Main St");

        userRepository.update(user);

        Map<String, Object> updatedUser = userRepository.findUserByEmail("hellotest@gmail.com");
        assertEquals("John Doe", updatedUser.get("name"));
        assertEquals("hellotest@gmail.com", updatedUser.get("email"));
        assertEquals("2004 Main St", updatedUser.get("adresse"));

        deleteUserSafely("hellotest@gmail.com");
    }

    private void deleteUserSafely(String email) {
        try {
            System.out.println("Attempting to delete user: " + email);
            userRepository.delete(email);
            System.out.println("User deleted successfully: " + email);
        } catch (RuntimeException e) {
            System.err.println("Failed to delete user " + email + ": " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up test data");
        deleteUserSafely("john.doe@example.com");
        deleteUserSafely("john.doe@aitouaguerd.com");
        deleteUserSafely("john.doe@anass.com");
        System.out.println("Cleanup completed");
    }
}