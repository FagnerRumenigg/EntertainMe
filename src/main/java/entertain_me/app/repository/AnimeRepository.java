package entertain_me.app.repository;

import entertain_me.app.dto.anime.AnimeReturnDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime;
import entertain_me.app.vo.AnimeVO;

import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

  @Query("SELECT new entertain_me.app.vo.AnimeVO("
  		+ "a.title, a.source, a.status, a.synopsys, a.episodes, a.year, a.demographics, a.studios, a.genres) "
  		+ "FROM Anime a WHERE a.title = %:title%")
  AnimeVO findAnimeByTitle(String title);

  Optional<AnimeReturnDto> findByJikanId(Integer jikanId);
}