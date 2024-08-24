package entertain_me.app.service;

import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.model.Demographic;
import entertain_me.app.model.Genre;
import entertain_me.app.repository.DemographicRepository;
import entertain_me.app.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Genre findOrCreateGenre(String name) {
        return genreRepository.findByName(name)
                .orElseGet(() -> genreRepository.save(new Genre(UUID.randomUUID(), name)));
    }

    public List<GenreDto> findGenreNameByAnimeIds(List<UUID> animeIds){
        return genreRepository.findDistinctNameByAnimes_IdIn(animeIds);
    }
}
