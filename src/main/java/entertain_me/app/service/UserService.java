package entertain_me.app.service;

import entertain_me.app.dto.user.ChangeEmailPasswordDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void changeEmail(ChangeEmailPasswordDto userDto) throws EmailNotValidException, AlreadyExistsException, IncorrectPasswordException {
        User user = userRepository.getByEmail(userDto.currentEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!isValidEmail(userDto.newEmail())) {
            log.info("This is not a valid e-mail.");
            throw new EmailNotValidException("This is not a valid e-mail");
        }

        Optional<User> emailExistsOptional = userRepository.getByEmail(userDto.newEmail());
        if (emailExistsOptional.isPresent() && !userDto.currentEmail().equals(userDto.newEmail())) {
            log.info("Email already exists.");
            throw new AlreadyExistsException("Already exists a user with this e-mail.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(userDto.password(), user.getPassword());
        if (!isPasswordMatch) {
            log.info("Incorrect password provided.");
            throw new IncorrectPasswordException("Incorrect password");
        }

        user.setEmail(userDto.newEmail());
        userRepository.save(user);
        log.info("Email updated successfully");
    }

    private static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
