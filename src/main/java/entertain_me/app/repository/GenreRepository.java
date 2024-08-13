package entertain_me.app.repository;

import entertain_me.app.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository  extends JpaRepository<Genre, UUID> {
}
