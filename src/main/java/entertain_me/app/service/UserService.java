package entertain_me.app.service;

import entertain_me.app.dto.user.AuthenticationDto;
import entertain_me.app.dto.user.ChangeEmailDto;
import entertain_me.app.dto.user.ChangePasswordDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void changePassword(ChangePasswordDto changePasswordDto) throws IncorrectPasswordException {
        User user = userRepository.getByEmail(changePasswordDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(isPasswordInvalid(changePasswordDto.newPassword())){
            throw new IncorrectPasswordException("Password it's not in the right pattern");
        }

        if(passwordEncoder.matches(changePasswordDto.currentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            userRepository.save(user);
        }else{
            log.info("Incorrect password provided.");
            throw new IncorrectPasswordException("Incorrect password");
        }
    }

    public void changeEmail(ChangeEmailDto changeEmailDto) throws EmailNotValidException, AlreadyExistsException, IncorrectPasswordException {

        User user = userRepository.getByEmail(changeEmailDto.currentEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (isEmailInvalid(changeEmailDto.newEmail())) {
            log.info("This is not a valid e-mail.");
            throw new EmailNotValidException("This is not a valid e-mail");
        }

        Optional<User> emailExistsOptional = userRepository.getByEmail(changeEmailDto.newEmail());
        if (emailExistsOptional.isPresent() && !changeEmailDto.currentEmail().equals(changeEmailDto.newEmail())) {
            log.info("Email already exists.");
            throw new AlreadyExistsException("Already exists a user with this e-mail.");
        }

        boolean isPasswordMatch = passwordEncoder.matches(changeEmailDto.password(), user.getPassword());
        if (!isPasswordMatch) {
            log.info("Incorrect password provided.");
            throw new IncorrectPasswordException("Incorrect password");
        }

        user.setEmail(changeEmailDto.newEmail());
        userRepository.save(user);
        log.info("Email updated successfully");
    }

    public void deleteAccount(AuthenticationDto userDto) throws IncorrectPasswordException {
        User user = userRepository.getByEmail(userDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(passwordEncoder.matches(userDto.password(), user.getPassword())){
            userRepository.delete(user);
        }else{
            log.info("Incorrect password provided.");
            throw new IncorrectPasswordException("Incorrect password");
        }
    }

    public static boolean isEmailInvalid(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return !matcher.matches();
    }

    public static boolean isPasswordInvalid(String password){
        String passwordPattern =
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";

        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }

}