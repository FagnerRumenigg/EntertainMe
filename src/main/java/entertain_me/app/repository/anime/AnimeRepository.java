package entertain_me.app.repository.anime;

import entertain_me.app.vo.AnimeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    @Query("SELECT new entertain_me.app.model.Anime(" +
            "a.id, a.title, a.source, a.status, a.ageRating, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Anime> findAllAnimeInfoByTitle(String title);

    @Query(value = "SELECT * FROM anime ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Anime> findRandomAnimes(@Param("limit") int limit);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.title, a.source, a.status, a.synopsys, a.episodes, a.year)" +
            "FROM Anime a " +
            "JOIN a.demographics d " +
            "WHERE d.id IN (:demographicsId)")
    List<AnimeVo> findAnimeByDemographic(@Param("demographicsId") List<Long> demographicsId);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.title, a.source, a.status, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "JOIN a.genres g " +
            "WHERE g.id IN (:genreIds)")
    List<AnimeVo> findAnimeByGenre(@Param("genreIds") List<Long> genreIds);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.title, a.source, a.status, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "JOIN a.studios s " +
            "WHERE s.id IN (:studioIds)")
    List<AnimeVo> findAnimeByStudio(@Param("studioIds") List<Long> studioIds);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.title, a.source, a.status, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "JOIN a.themes t " +
            "WHERE t.id IN (:themeIds)")
    List<AnimeVo> findAnimeByTheme(@Param("themeIds") List<Long> themeIds);

}