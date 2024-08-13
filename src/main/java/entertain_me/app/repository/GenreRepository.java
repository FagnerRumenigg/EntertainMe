package entertain_me.app.repository;

import entertain_me.app.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository  extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}
