package entertain_me.app.service;

import entertain_me.app.dto.jikan_api.JikanAnimeStreamingDto;
import entertain_me.app.model.Anime.AnimeStreaming;
import entertain_me.app.repository.anime.AnimeStreamingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeStreamingService {
    @Autowired
    AnimeStreamingRepository animeStreamingRepository;
    @Transactional
    public void save(List<JikanAnimeStreamingDto> animeStreamingDtoList){
        for(JikanAnimeStreamingDto animeStreamingDto : animeStreamingDtoList){
            AnimeStreaming animeStreaming = new AnimeStreaming(animeStreamingDto.animeId() , animeStreamingDto.name(), animeStreamingDto.url());

            animeStreamingRepository.save(animeStreaming);
        }
    }
}
