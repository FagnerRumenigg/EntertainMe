package entertain_me.app.repository.anime;

import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AllAnimeInfoVoUnique;
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

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE d.id IN (:demographicsId)")
    List<AllAnimeInfoVoUnique> findAnimeByDemographic(@Param("demographicsId") List<Long> demographicsId);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE g.id IN (:genreIds)")
    List<AllAnimeInfoVoUnique> findAnimeByGenre(@Param("genreIds") List<Long> genreIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE s.id IN (:studioIds)")
    List<AllAnimeInfoVoUnique> findAnimeByStudio(@Param("studioIds") List<Long> studioIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE t.id IN (:themeIds)")
    List<AllAnimeInfoVoUnique> findAnimeByTheme(@Param("themeIds") List<Long> themeIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE d.id NOT IN (:demographicsId)")
    List<AllAnimeInfoVoUnique> findAnimeByOtherDemographic(@Param("demographicsId") List<Long> demographicsId);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE g.id NOT IN (:genreIds)")
    List<AllAnimeInfoVoUnique> findAnimeByOtherGenre(@Param("genreIds") List<Long> genreIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE s.id NOT IN (:studioIds)")
    List<AllAnimeInfoVoUnique> findAnimeByOtherStudio(@Param("studioIds") List<Long> studioIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE t.id NOT IN (:themeIds)")
    List<AllAnimeInfoVoUnique> findAnimeByOtherTheme(@Param("themeIds") List<Long> themeIds);

    @Query("SELECT new entertain_me.app.vo.AllAnimeInfoVoUnique(" +
            "a.title, " +
            "a.source, " +
            "a.status, " +
            "a.ageRating, " +
            "a.synopsys, " +
            "a.episodes, " +
            "a.year, " +
            "d.name, " +
            "s.name, " +
            "g.name, " +
            "t.name) " +
            "FROM Anime a " +
            "LEFT JOIN a.demographics d " +
            "LEFT JOIN a.genres g " +
            "LEFT JOIN a.studios s " +
            "LEFT JOIN a.themes t " +
            "WHERE a.id IN :ids")
    List<AllAnimeInfoVoUnique> findEntertainMeTeamFavoriteAnimes(@Param("ids") List<Long> ids);

}