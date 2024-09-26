package entertain_me.app.repository.anime;

import entertain_me.app.model.Anime.AnimeLanguages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeLanguagesRepository extends JpaRepository<AnimeLanguages, Long> {

    @Query("SELECT al FROM AnimeLanguages al where al.idAnime = :animeId AND al.officialTranslate = false")
    AnimeLanguages findByIdAndOfficialTranslateIsFalse(Integer animeId);
}
