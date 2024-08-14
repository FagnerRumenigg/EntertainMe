package entertain_me.app.repository;

import entertain_me.app.dto.anime.AnimeDatabaseDto;
import entertain_me.app.vo.AllAnimeInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime;
import entertain_me.app.vo.AnimeVo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, UUID> {

    @Query("SELECT new entertain_me.app.model.Anime(" +
            "a.id, a.title, a.source, a.status, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Anime> findAllAnimeInfoByTitle(String title);
}