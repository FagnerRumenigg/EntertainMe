package entertain_me.app.repository;

import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Optional<Theme> findByName(String name);

    @Query("SELECT new entertain_me.app.dto.anime.ThemeDto(a.id, t.name) " +
            "FROM Theme t " +
            "JOIN t.animes a " +
            "WHERE a.id IN :animeIds")
    List<ThemeDto> findDistinctNameByAnimes_IdIn(List<Long> animeIds);

    @Query("SELECT new entertain_me.app.dto.anime.ThemeDto(t.id, t.name) " +
            "FROM Theme t ")
    List<ThemeDto> findAllTheme();

}
