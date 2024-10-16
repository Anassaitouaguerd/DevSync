package com.Testify;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testDisplayUsers(){
        User user = new User("John Ait Ouaguerd", "john.doe@aitouaguerd.com", "password123", "123 Main St", false);
        User user2 = new User("John anass", "john.doe@anass.com", "anass2323", "321 Main St", true);
        userRepository.create(user);
        userRepository.create(user2);
        List<User> users = userRepository.display();
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("John Ait Ouaguerd")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("John anass")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("john.doe@aitouaguerd.com")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("john.doe@anass.com")));
    }

    @Test
    public void testCreateSingleUser() {
        User user = new User("John Doe", "john.doe@example.com", "password123", "123 Main St", false);
        assertDoesNotThrow(() -> userRepository.create(user));
    }

}
