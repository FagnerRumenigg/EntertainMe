package entertain_me.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import entertain_me.app.model.User;
import entertain_me.app.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	@Autowired 
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByLogin(username);
	}
	
	public UserDetails findByLogin(String userName)throws UsernameNotFoundException {
		return repository.findByLogin(userName);
	}
	
	public void save(User user) {
		repository.save(user);
	}

}
