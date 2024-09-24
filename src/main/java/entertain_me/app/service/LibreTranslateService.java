package entertain_me.app.service;

import entertain_me.app.component.Helper;
import entertain_me.app.dto.TranslateInfoDto;
import entertain_me.app.dto.TranslateReturnDto;
import entertain_me.app.model.Anime.AnimeLanguages;
import entertain_me.app.repository.anime.AnimeLanguagesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class LibreTranslateService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private Helper helper;

    @Autowired
    private AnimeLanguagesService animeLanguagesService;

    private final RestTemplate restTemplate = new RestTemplate();

    public void processTranslateRequest() {
        String apiUrl = "http://127.0.0.1:5000/translate";

        List<TranslateInfoDto> allAnimeStatus = animeService.getAllAnimeStatus();
        translateRequest(apiUrl, allAnimeStatus, "status");

        List<TranslateInfoDto> allAnimeAgeRating = animeService.getAllAnimeAgeRating();
        translateRequest(apiUrl, allAnimeAgeRating, "ageRating");


        List<TranslateInfoDto> allAnimeSynopsys = animeService.getAllAnimeSynopsis();
        translateRequest(apiUrl, allAnimeSynopsys, "synopsys");

    }

    private void translateRequest(String apiUrl, List<TranslateInfoDto> list, String field){
        list.forEach(anime -> {

            TranslateInfoDto translateRequestDto = new TranslateInfoDto(anime.animeId(), anime.q(), "en", "pt");

            ResponseEntity<TranslateReturnDto> responseEntity = restTemplate.postForEntity(apiUrl, translateRequestDto, TranslateReturnDto.class);

            TranslateReturnDto responseBody = responseEntity.getBody() ;

            animeLanguagesService.saveAnimeLanguage(anime.animeId(), responseBody == null ? "" : responseBody.translatedText(), field);


        });
    }
}
