package entertain_me.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import entertain_me.app.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	UserDetails findByLogin(String login);
}
