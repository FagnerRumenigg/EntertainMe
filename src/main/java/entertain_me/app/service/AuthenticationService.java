package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.user.RegisterDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import entertain_me.app.model.User;
import entertain_me.app.repository.user.UserRepository;

@Log4j2
@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired 
	UserRepository repository;

	@Autowired
	AddressService addressService;

	@Autowired
	UserService userService;

	@Autowired
	TokenServiceConfig tokenServiceConfig;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByLogin(username);
	}

	public UserDetails findByLogin(String userName) throws UsernameNotFoundException, DataAccessResourceFailureException {
		return repository.findByEmail(userName);
	}

	public void save(RegisterDto registerUser) throws AlreadyExistsException, EmailNotValidException, IncorrectPasswordException {
		if(UserService.isEmailInvalid(registerUser.email())){
			throw new EmailNotValidException("This is not a valid e-mail");
		}

		if (findByLogin(registerUser.email()) != null) {
			throw new AlreadyExistsException("Already exist an user with this e-mail.");
		}

		if(UserService.isPasswordInvalid(registerUser.password())){
			throw new IncorrectPasswordException("Password it's not in the right pattern");
		}

		String encryptedPassword = new BCryptPasswordEncoder().encode(registerUser.password());
		User newUser = new User(registerUser.name(), registerUser.email(), encryptedPassword, registerUser.role());

		repository.save(newUser);
		log.info("User created");

	}

	public void logout(String token) {
		String jti = tokenServiceConfig.getJtiFromToken(token);
		long expiration = tokenServiceConfig.getExpirationFromToken(token);

		tokenServiceConfig.addToBlacklist(jti, expiration);
		log.info("User logout");

	}
}
