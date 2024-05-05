package entertain_me.app.service;

import entertain_me.app.dto.user.RegisterDto;
import entertain_me.app.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	@Autowired 
	UserRepository repository;

	@Autowired
	AddressService addressService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByLogin(username);
	}

	public UserDetails findByLogin(String userName) throws UsernameNotFoundException {
		return repository.findByEmail(userName);
	}

	public void save(RegisterDto registerUser) throws AlreadyExistsException {
		if (findByLogin(registerUser.email()) != null) {
			throw new AlreadyExistsException("Already exist an user with this e-mail.");
		}

		String encryptedPassword = new BCryptPasswordEncoder().encode(registerUser.password());
		User newUser = new User(registerUser.name(), registerUser.email(), encryptedPassword, registerUser.role());

		repository.save(newUser);
	}
}
