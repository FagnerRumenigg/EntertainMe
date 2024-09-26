package entertain_me.app.service;

import entertain_me.app.model.Anime.AnimeLanguages;
import entertain_me.app.repository.anime.AnimeLanguagesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AnimeLanguagesService {

    @Autowired
    private AnimeLanguagesRepository animeLanguagesRepository;

    public void saveAnimeLanguage(Long animeId, String textTranslated, String field, boolean isAzureOfficialTranslate){
        log.info("animeId {} - field {}", animeId, field);
        AnimeLanguages animeLanguages = getAnimeLanguages(animeId);

        animeLanguages.setIdAnime(animeId);
        animeLanguages.setLanguage("pt");
        animeLanguages.setOfficialTranslate(isAzureOfficialTranslate);

        switch(field){
            case "status":
                animeLanguages.setStatus(textTranslated);
                break;
            case "ageRating":
                animeLanguages.setAgeRating(textTranslated);
                break;
            case "synopsys":
                animeLanguages.setSynopsys(textTranslated);
        }

        animeLanguagesRepository.save(animeLanguages);
    }

    public AnimeLanguages getAnimeLanguages(Long animeId) {
        return animeLanguagesRepository.findById(animeId)
                .orElse(new AnimeLanguages());
    }

}
