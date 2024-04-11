package entertain_me.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import entertain_me.app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	UserDetails findByLogin(String login);
}
