package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.enums.UserRole;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.UserRegistrationDTO;
import com.example.eventmanagement.repositories.UserRepository;
import com.example.eventmanagement.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("johndoe@gmail.com");
        testUser.setPassword("password");
    }

    @AfterEach
    void tearDown() {
        testUser = null;
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("johndoe@gmail.com", createdUser.getEmail());

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void getUserByEmail_Success() {
        when(userRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserByEmail("johndoe@gmail.com");

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals("johndoe@gmail.com", foundUser.getEmail());

        verify(userRepository, times(1)).findByEmail("johndoe@gmail.com");
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail("nonexistant@gmail.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUserByEmail("nonexistant@gmail.com"));

        verify(userRepository, times(1)).findByEmail("nonexistant@gmail.com");
    }

    @Test
    void getUserById_Success() {
        testUser.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserById(3L);

        assertNotNull(foundUser);
        assertEquals(3L, foundUser.getId());
        assertEquals("John Doe", foundUser.getName());

        verify(userRepository, times(1)).findById(3L);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUserById(99L));

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void registerNewUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("John Doe");
        registrationDTO.setEmail("johndoe@gmail.com");
        registrationDTO.setPassword("password");
        registrationDTO.setRole(UserRole.EMPLOYEE);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setPassword("encodedPassword");
            return user;
        });

        User registeredUser = userService.registerNewUser(registrationDTO);
        assertNotNull(registeredUser);
        assertEquals("John Doe", registeredUser.getName());
        assertEquals("johndoe@gmail.com", registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());

        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

}
