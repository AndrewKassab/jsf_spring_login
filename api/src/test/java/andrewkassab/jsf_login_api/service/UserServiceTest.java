package andrewkassab.jsf_login_api.service;

import andrewkassab.jsf_login_api.entity.User;
import andrewkassab.jsf_login_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        String username = "testUser";
        String rawPassword = "password";
        String encodedPassword = "$2a$10$...";
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        Optional<User> result = userService.login(username, rawPassword);

        assertTrue(result.isPresent(), "User should be present");
        assertEquals(user, result.get(), "Returned user should match the repository user");
    }

    @Test
    void testLogin_Failure() {
        String username = "testUser";
        String rawPassword = "wrongPassword";
        String encodedPassword = "$2a$10$...";
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        Optional<User> result = userService.login(username, rawPassword);

        assertFalse(result.isPresent(), "User should not be present with incorrect password");
    }

    @Test
    void testLogin_UserNotFound() {
        String username = "nonexistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = userService.login(username, "password");

        assertFalse(result.isPresent(), "User should not be found");
    }

    @Test
    void testSaveUser_EncodesPasswordBeforeSaving() {
        String rawPassword = "password";
        String encodedPassword = "$2a$10$...";
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(rawPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        userService.saveUser(user);

        assertEquals(encodedPassword, user.getPassword(), "Password should be encoded before saving");
        verify(userRepository).save(user);  // Ensure the user is saved
    }

}
