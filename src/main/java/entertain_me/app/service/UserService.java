package entertain_me.app.service;

import entertain_me.app.dto.user.UserDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void changeEmail(UserDto userDto) throws EmailNotValidException, AlreadyExistsException {
        Optional<User> userOptional = userRepository.findById(userDto.id());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String newEmail = userDto.email();

        if (isValidEmail(newEmail)) {
            Optional<User> emailExistsOptional = userRepository.getByEmail(newEmail);
            log.info("DTO: "+userDto);
            //log.info("Email: "+ emailExistsOptional.get());
            if (emailExistsOptional.isPresent() && userDto.id().equals(emailExistsOptional.get().getId())) {

                user.setEmail(newEmail);
                userRepository.save(user);
                log.info("Email updated successfully");
            } else {
                log.info("Email already exists.");
                throw new AlreadyExistsException("Already exists a user with this e-mail.");
            }
        } else {
            log.info("This is not a valid e-mail.");
            throw new EmailNotValidException("This is not a valid e-mail");
        }
    }


    private static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
