package entertain_me.app.service;

import entertain_me.app.model.Anime;
import entertain_me.app.record.anime.AnimeReturn;
import entertain_me.app.record.jikan_api.*;
import entertain_me.app.repository.AnimeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JikanService {

    private final JikanAPIService jikanAPIService;

    public JikanService(JikanAPIService jikanAPIService) {
        this.jikanAPIService = jikanAPIService;
    }

    @Autowired
    private AnimeRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(JikanService.class);

    public void getAllAnimesJikan() throws Exception {
        try {
            int page = 1;
            boolean returnOk = true;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime timeStart = LocalDateTime.now();
            logger.info("Started: " + LocalDateTime.now().format(format));

            while (returnOk) {
                String apiUrl = String.format("https://api.jikan.moe/v4/anime?page=%d", page);

                List<JikanRequestAllRecord> animesList = jikanAPIService.requestAllAnimes(apiUrl);

                if (animesList.isEmpty()) {
                	returnOk = false;
                } else {
                    List<AnimeReturn> retorno = animesList.stream()
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
                    for (AnimeReturn anime : retorno) {
                    	logger.info("Anime inserted: "+ anime.title());
                        repository.save(setAnimeFromJikan(anime));
                    }
                }
                Duration diferenca = Duration.between(timeStart, LocalDateTime.now());
                long minutosPassados = diferenca.toMinutes();

                if(minutosPassados > 5){
                    logger.info("Page: "+page);
                }
                page++;
                logger.info("finished the page : " + page + " - " + LocalDateTime.now().format(format));
                Thread.sleep(1500);
            }
           logger.info("Terminou: " + LocalDateTime.now().format(format));
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