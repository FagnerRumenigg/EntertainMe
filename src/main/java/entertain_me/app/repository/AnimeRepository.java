package entertain_me.app.repository;

import entertain_me.app.dto.anime.AnimeDatabaseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime;
import entertain_me.app.vo.AnimeVo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, UUID> {

    List<AnimeVo> findByTitleContainingIgnoreCase(String title);

    Optional<AnimeDatabaseDto> findByJikanId(Integer jikanId);
}