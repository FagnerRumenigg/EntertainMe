import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import entertain_me.app.dto.user.ChangeEmailDto;
import entertain_me.app.dto.user.ChangePasswordDto;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void testChangePassword_UserNotFound() {
        when(userRepository.getByEmail(anyString())).thenReturn(Optional.empty());

        ChangePasswordDto dto = new ChangePasswordDto("test@example.com", "currentPassword", "newPassword");

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.changePassword(dto));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testChangePassword_IncorrectCurrentPassword() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("currentPassword")); // Hashed password

        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ChangePasswordDto dto = new ChangePasswordDto("test@example.com", "currentPassword", "newPassword@1");

        IncorrectPasswordException exception = assertThrows(IncorrectPasswordException.class, () -> userService.changePassword(dto));
        assertEquals("Incorrect password", exception.getMessage());
    }

    @Test
    public void testChangePassword_InvalidNewPassword() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("currentPassword")); // Hashed password

        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ChangePasswordDto dto = new ChangePasswordDto("test@example.com", "currentPassword", "newPassword@");

        IncorrectPasswordException exception = assertThrows(IncorrectPasswordException.class, () -> userService.changePassword(dto));
        assertEquals("Password it's not in the right pattern", exception.getMessage());
    }

    @Test
    public void testChangePassword_Success() throws IncorrectPasswordException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("currentPassword")); // Hashed password

        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("currentPassword", user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword@1")).thenReturn("encodedNewPassword");

        ChangePasswordDto dto = new ChangePasswordDto("test@example.com", "currentPassword", "newPassword@1");

        userService.changePassword(dto);

        verify(userRepository, times(1)).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    public void testChangeEmail_Success() throws Exception {
        ChangeEmailDto userDto = new ChangeEmailDto("current@example.com", "new@example.com", "password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(passwordEncoder.encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        userService.changeEmail(userDto);

        verify(userRepository).save(user);
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    public void testChangeEmail_EmailNotValid() {
        ChangeEmailDto userDto = new ChangeEmailDto("current@example.com", "invalid-email", "password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(passwordEncoder.encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));

        EmailNotValidException exception = assertThrows(EmailNotValidException.class, () -> userService.changeEmail(userDto));
        assertEquals("This is not a valid e-mail", exception.getMessage());
    }

    @Test
    public void testChangeEmail_EmailAlreadyExists() {
        ChangeEmailDto userDto = new ChangeEmailDto("current@example.com", "new@example.com", "password");

        User currentUser = new User();
        currentUser.setEmail("current@example.com");
        currentUser.setPassword(passwordEncoder.encode("password")); // Hashed password

        User existingUser = new User();
        existingUser.setEmail("new@example.com");

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(currentUser));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.of(existingUser));

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> userService.changeEmail(userDto));
        assertEquals("Already exists a user with this e-mail.", exception.getMessage());
    }

    @Test
    public void testChangeEmail_IncorrectPassword() {
        ChangeEmailDto userDto = new ChangeEmailDto("current@example.com", "new@example.com", "wrong-password");

        User user = new User();
        user.setEmail("current@example.com");
        user.setPassword(passwordEncoder.encode("password")); // Hashed password

        when(userRepository.getByEmail("current@example.com")).thenReturn(Optional.of(user));
        when(userRepository.getByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.matches("wrong-password", user.getPassword())).thenReturn(false);

        IncorrectPasswordException exception = assertThrows(IncorrectPasswordException.class, () -> userService.changeEmail(userDto));
        assertEquals("Incorrect password", exception.getMessage());
    }
}
