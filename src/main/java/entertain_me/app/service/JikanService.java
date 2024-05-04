package entertain_me.app.service;

import entertain_me.app.model.Anime;
import entertain_me.app.dto.anime.AnimeReturn;
import entertain_me.app.dto.jikan_api.*;
import entertain_me.app.repository.AnimeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log
@Service
public class JikanService {

    private final JikanAPIService jikanAPIService;

    public JikanService(JikanAPIService jikanAPIService) {
        this.jikanAPIService = jikanAPIService;
    }

    @Autowired
    private AnimeRepository repository;

    public void getAllAnimesJikan() throws Exception {
        try {
            int page = 1;
            boolean returnOk = true;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime timeStart = LocalDateTime.now();

            log.info("Updating database , time started: "+ timeStart);
            while (returnOk) {

                List<JikanRequestAllRecord> animesList = jikanAPIService.requestAllAnimes(page);

                if (!animesList.isEmpty()) {
                    List<AnimeReturn> animesReturn = animesList.stream()
                            .map(anime -> new AnimeReturn(
                                    anime.mal_id(),
                                    anime.title(),
                                    anime.source(),
                                    anime.status(),
                                    anime.synopsis(),
                                    anime.episodes(),
                                    anime.year(),
                                    getNameFromDemographics(anime.demographics()),
                                    getNameFromStudio(anime.studios()),
                                    getNameFromGenres(anime.genres())))
                            .toList();
                    for (AnimeReturn anime : animesReturn) {
                        Optional<AnimeReturn> animeDatabase = repository.findByJikanId(anime.jikanId());

                        if(animeDatabase.isPresent()){
                            log.info("Anime already registered: "+ anime.title());
                            continue;
                        }
                        log.info("Anime saved: "+anime.title());
                        repository.save(setAnimeFromJikan(anime));
                    }
                }else{
                    returnOk = false;
                }
                Duration difference = Duration.between(timeStart, LocalDateTime.now());
                long passedMinutes = difference.toMinutes();

                if(passedMinutes >= 5){
                    timeStart = LocalDateTime.now();
                    log.info("5 minutes passed, page : " + page + " - " + timeStart.format(format));
                }
                page++;

                Thread.sleep(1500);
            }
        } catch (Exception e) {
            throw new Exception("Fail updating database", e);
        }
    }

    public String getAnimeNews(Integer jikanId){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

        return now.format(format);
    }

    private static Anime setAnimeFromJikan(AnimeReturn anime) {
        Anime animeNovo = new Anime();

        animeNovo.setJikanId(anime.jikanId());
        animeNovo.setTitle(anime.title());
        animeNovo.setSource(anime.source());
        animeNovo.setStatus(anime.status());
        animeNovo.setSynopsys(anime.synopsys());
        animeNovo.setEpisodes(anime.episodes());
        animeNovo.setYear(anime.year());
        animeNovo.setDemographics(anime.demographics());
        animeNovo.setStudios(anime.studios());
        animeNovo.setGenres(anime.genres());

        return animeNovo;
    }

    private static List<String> getNameFromGenres(List<Genre> genres) {
        return genres.stream()
                .map(Genre::name)
                .collect(Collectors.toList());
    }

    private static List<String> getNameFromStudio(List<Studio> studio) {
        return studio.stream()
                .map(Studio::name)
                .collect(Collectors.toList());
    }

    private static List<String> getNameFromDemographics(List<Demographics> demographics) {
        return demographics.stream()
                .map(Demographics::name)
                .collect(Collectors.toList());
    }
}