package entertain_me.app.repository;


import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.model.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DemographicRepository extends JpaRepository<Demographic, UUID> {

    Optional<Demographic> findByName(String name);

    @Query("SELECT new entertain_me.app.dto.anime.DemographicDto(a.id, d.name) " +
            "FROM Demographic d " +
            "JOIN d.animes a " +
            "WHERE a.id IN :animeIds")
    List<DemographicDto> findDistinctNameByAnimes_IdIn(List<Long> animeIds);


}
