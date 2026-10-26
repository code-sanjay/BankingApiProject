package com.example.banking_api.Repository;

import com.example.banking_api.Entity.Role;
import com.example.banking_api.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_returns_correct_user() {
        // Arrange
        User user = User.builder()
                .username("sanjay")
                .email("sanjay@bank.com")
                .password("hashedpassword")
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("sanjay@bank.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("sanjay", found.get().getUsername());
        assertEquals(Role.CUSTOMER, found.get().getRole());
    }

    @Test
    void findByEmail_returns_empty_for_unknown_email() {
        Optional<User> found = userRepository.findByEmail("unknown@bank.com");
        assertFalse(found.isPresent());
    }

    @Test
    void existsByUsername_returns_true_when_exists() {
        User user = User.builder()
                .username("sanjay")
                .email("sanjay@bank.com")
                .password("hashedpassword")
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("sanjay"));
    }
}
