package entertain_me.app.repository;

import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.model.Demographic;
import entertain_me.app.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    Optional<Studio> findByName(String name);

    @Query("SELECT new entertain_me.app.dto.anime.StudioDto(a.id, s.name) " +
            "FROM Studio s " +
            "JOIN s.animes a " +
            "WHERE a.id IN :animeIds")
    List<StudioDto> findDistinctNameByAnimes_IdIn(List<Long> animeIds);

    @Query("SELECT new entertain_me.app.dto.anime.StudioDto(s.id, s.name) " +
            "FROM Studio s ")
    List<StudioDto> findAllStudio();

}
