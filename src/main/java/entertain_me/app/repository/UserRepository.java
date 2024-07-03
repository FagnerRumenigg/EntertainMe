package entertain_me.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import entertain_me.app.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

	UserDetails findByEmail(String login);

	Optional<User> getByEmail(String email);
}