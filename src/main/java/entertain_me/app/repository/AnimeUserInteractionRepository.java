package entertain_me.app.repository;

import entertain_me.app.dto.anime.AnimeUserInteractionDto;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractions;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeUserInteractionRepository extends JpaRepository<AnimeUserInteractions, AnimeUserInteractionId> {

    @Query("SELECT new com.seu.pacote.AnimeUserInteractionDto(ai.id.idAnime, ai.rating, ai.noInterest, ai.watched) " +
            "FROM AnimeUserInteractions ai " +
            "WHERE ai.id.idUser = :idUser")
    List<AnimeUserInteractionDto> findInteractionsByUserId(@Param("idUser") Long idUser);

}
