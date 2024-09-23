package entertain_me.app.repository;

import entertain_me.app.dto.anime.AnimeUserInteractionDto;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractions;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractionId;
import entertain_me.app.vo.AllAnimeInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeUserInteractionRepository extends JpaRepository<AnimeUserInteractions, AnimeUserInteractionId> {

    @Query("SELECT new entertain_me.app.dto.anime.AnimeUserInteractionDto(" +
            "ai.id.idAnime, ai.rating, ai.noInterest, ai.watching, ai.myList, ai.watched) " +
            "FROM AnimeUserInteractions ai " +
            "WHERE ai.id.idUser = :idUser")
    List<AnimeUserInteractionDto> findInteractionsByUserId(@Param("idUser") Long idUser);

    @Query("SELECT ai.id.idAnime " +
            "FROM AnimeUserInteractions ai " +
            "WHERE ai.id.idUser = :idUser " +
            "AND ai.myList = true")
    List<Long> findInteractionsByUserIdAndMyList(Long idUser);

    @Query("SELECT ai.id.idAnime " +
            "FROM AnimeUserInteractions ai " +
            "WHERE ai.id.idUser = :idUser " +
            "AND ai.watching = true")
    List<Long> findInteractionsByUserIdAndIsWatching(Long idUser);


    @Query("SELECT ai.id.idAnime " +
            "FROM AnimeUserInteractions ai " +
            "WHERE ai.id.idUser = :idUser " +
            "AND ai.watched = true")
    List<Long> findInteractionsByUserIdAndIsWatched(Long idUser);
}