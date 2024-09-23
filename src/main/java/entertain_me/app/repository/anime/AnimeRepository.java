package entertain_me.app.repository.anime;

import entertain_me.app.dto.anime.JikanAnimeIdsDto;
import entertain_me.app.vo.AnimeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime.Anime;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, a.title, a.source, a.status, a.ageRating, a.synopsys, a.episodes, a.year) " +
            "FROM Anime a " +
            "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<AnimeVo> findAllAnimeInfoByTitle(String title, Pageable pageable);

    @Query(value = "SELECT * FROM anime ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Anime> findRandomAnimes(@Param("limit") int limit);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes," +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "WHERE d.id IN (:demographicsId)")
    Page<AnimeVo> findAnimeByDemographic(List<Long> demographicsId, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.genres g " +
            "WHERE g.id IN (:genreIds)")
    Page<AnimeVo> findAnimeByGenre(List<Long> genreIds, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.studios s " +
            "WHERE s.id IN (:studioIds)")
    Page<AnimeVo> findAnimeByStudio(List<Long> studioIds, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.themes t " +
            "WHERE t.id IN (:themeIds)")
    Page<AnimeVo> findAnimeByTheme(List<Long> themeIds, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "WHERE d.id NOT IN (:demographicsId)")
    List<AnimeVo> findAnimeByOtherDemographic(List<Long> demographicsId, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.genres g " +
            "WHERE g.id NOT IN (:genreIds)")
    List<AnimeVo> findAnimeByOtherGenre(List<Long> genreIds, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.studios s " +
            "WHERE s.id NOT IN (:studioIds)")
    List<AnimeVo> findAnimeByOtherStudio(List<Long> studioIds, Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "LEFT JOIN a.themes t " +
            "WHERE t.id NOT IN (:themeIds)")
    List<AnimeVo> findAnimeByOtherTheme(List<Long> themeIds,  Pageable pageable);

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year) " +
            "FROM Anime a " +
            "WHERE a.id IN :ids")
    Page<AnimeVo> findEntertainMeTeamFavoriteAnimes(List<Long> ids, Pageable pageable);

    @Query("SELECT new entertain_me.app.dto.anime.JikanAnimeIdsDto(a.id, a.jikanId) FROM Anime a")
    List<JikanAnimeIdsDto> findAllIds();

    @Query("SELECT new entertain_me.app.vo.AnimeVo(" +
            "a.id, " +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes," +
            "a.year) " +
            "FROM Anime a " +
            "WHERE a.id IN (:animesId)")
    List<AnimeVo> findAnimeById(List<Long> animesId);
}