package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repositories.UserRepository;
import com.example.eventmanagement.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_ShouldReturnSavedUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("Test User");

        User savedUser = userService.createUser(user);

        assertThat(savedUser.getId()).isNotNull();

        assertThat(savedUser.getEmail()).isEqualTo("test@gmail.com");
        assertThat(savedUser.getName()).isEqualTo("Test User");
    }

    @Test
    void getUserByEmail_ShouldReturnUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        userRepository.save(user);

        User foundUser = userService.getUserByEmail("test@example.com");

        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser.getName()).isEqualTo("Test User");
    }

//    @Test
//    void getUserByEmail_ShouldThrowException_WhenUserNotFound() {
//        assertThatThrownBy(() ->
//                userService.getUserByEmail("nonexistent@example.com"))
//                .isInstanceOf(UsernameNotFoundException.class)
//                .hasMessageContaining("User not found");
//    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }
}
