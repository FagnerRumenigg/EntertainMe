package entertain_me.app.repository.user;

import entertain_me.app.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{

	UserDetails findByEmail(String login);

	Optional<User> getByEmail(String email);

}