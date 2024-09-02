package entertain_me.app.repository;

import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.model.Demographic;
import entertain_me.app.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository  extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);

    @Query("SELECT new entertain_me.app.dto.anime.GenreDto(a.id, g.name) " +
            "FROM Genre g " +
            "JOIN g.animes a " +
            "WHERE a.id IN :animeIds")
    List<GenreDto> findDistinctNameByAnimes_IdIn(List<Long> animeIds);
}
