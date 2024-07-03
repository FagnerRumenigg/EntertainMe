import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import entertain_me.app.dto.user.ChangeEmailPasswordDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;
import entertain_me.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangeEmail_Success() throws Exception {
        ChangeEmailPasswordDto userDto = new ChangeEmailPasswordDto("current@example.com", "new@example.com", "password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        userService.changeEmail(userDto);

        verify(userRepository).save(user);
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    public void testChangeEmail_EmailNotValid() {
        ChangeEmailPasswordDto userDto = new ChangeEmailPasswordDto("current@example.com", "invalid-email", "password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(EmailNotValidException.class, () -> {
            userService.changeEmail(userDto);
        });

        assertEquals("This is not a valid e-mail", exception.getMessage());
    }

    @Test
    public void testChangeEmail_EmailAlreadyExists() {
        ChangeEmailPasswordDto userDto = new ChangeEmailPasswordDto("current@example.com", "new@example.com", "password");

        User currentUser = new User();
        currentUser.setEmail("current@example.com");
        currentUser.setPassword(new BCryptPasswordEncoder().encode("password")); // Hashed password

        User existingUser = new User();
        existingUser.setEmail("new@example.com");

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(currentUser));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.changeEmail(userDto);
        });

        assertEquals("Already exists a user with this e-mail.", exception.getMessage());
    }

    @Test
    public void testChangeEmail_IncorrectPassword() {
        ChangeEmailPasswordDto userDto = new ChangeEmailPasswordDto("current@example.com", "new@example.com", "wrong-password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("wrong-password", user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(IncorrectPasswordException.class, () -> {
            userService.changeEmail(userDto);
        });

        assertEquals("Incorrect password", exception.getMessage());
    }
}