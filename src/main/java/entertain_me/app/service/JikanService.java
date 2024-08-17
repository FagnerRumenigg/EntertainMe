package entertain_me.app.service;

import entertain_me.app.dto.anime.AnimeDatabaseDto;
import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import entertain_me.app.model.Anime;
import entertain_me.app.model.Demographic;
import entertain_me.app.model.Genre;
import entertain_me.app.model.Studio;
import entertain_me.app.repository.AnimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JikanService {

    private final JikanAPIService jikanAPIService;
    private final AnimeRepository repository;
    private final GenreService genreService;
    private final StudioService studioService;
    private final DemographicService demographicService;

    @Autowired
    public JikanService(JikanAPIService jikanAPIService, AnimeRepository repository, GenreService genreService, StudioService studioService, DemographicService demographicService) {
        this.jikanAPIService = jikanAPIService;
        this.repository = repository;
        this.genreService = genreService;
        this.studioService = studioService;
        this.demographicService = demographicService;
    }

    public void getAllAnimesJikan() throws Exception {
        try {
            int page = 1;
            boolean returnOk = true;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime timeStart = LocalDateTime.now();

            while (returnOk) {
                List<JikanResponseDataDto> animesList = jikanAPIService.requestAllAnimes(page);
                log.info("Returned "+animesList.size()+" animes at the page: "+page);

                if (!animesList.isEmpty()) {
                    List<AnimeDatabaseDto> animesReturn = animesList.stream()
                            .map(anime -> new AnimeDatabaseDto(
                                    anime.mal_id(),
                                    anime.title(),
                                    anime.source(),
                                    anime.status(),
                                    anime.synopsis(),
                                    anime.episodes(),
                                    anime.year(),
                                    anime.demographicsName(),
                                    anime.studiosName(),
                                    anime.genresName()))
                            .toList();
                    for (AnimeDatabaseDto anime : animesReturn) {
//                        Optional<AnimeDatabaseDto> animeDatabase = repository.findByJikanId(anime.jikanId());
//
//                        if (animeDatabase.isPresent()) {
//                            log.info("Anime {} already registered: ",anime.title());
//                            continue;
//                        }
                        log.info("Anime {} registered: ",anime.title());
                        Anime newAnime = setAnimeFromJikan(anime);
                        repository.save(newAnime);
                    }
                } else {
                    returnOk = false;
                }

                Duration difference = Duration.between(timeStart, LocalDateTime.now());
                long passedMinutes = difference.toMinutes();

                if (passedMinutes >= 5) {
                    timeStart = LocalDateTime.now();
                    log.info("5 minutes passed, page: {}  - {}", page, timeStart.format(format));
                }
                page++;
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            log.error("Fail updating database", e);
            throw new Exception("Fail updating database", e);
        }
    }

    private Anime setAnimeFromJikan(AnimeDatabaseDto anime) {
        Anime animeNovo = new Anime();
        animeNovo.setJikanId(anime.jikanId());
        animeNovo.setTitle(anime.title());
        animeNovo.setSource(anime.source());
        animeNovo.setStatus(anime.status());
        animeNovo.setSynopsys(anime.synopsys());
        animeNovo.setEpisodes(anime.episodes());
        animeNovo.setYear(anime.year());

        Set<Genre> genres = saveGenres(anime.genres());
        Set<Studio> studios = saveStudios(anime.studios());
        Set<Demographic> demographics = saveDemographics(anime.demographics());

        animeNovo.setGenres(genres);
        animeNovo.setStudios(studios);
        animeNovo.setDemographics(demographics);

        return animeNovo;
    }

    private Set<Genre> saveGenres(List<String> genreNames) {
        return genreNames.stream()
                .map(genreService::findOrCreateGenre)
                .collect(Collectors.toSet());
    }

    private Set<Studio> saveStudios(List<String> studioNames) {
        return studioNames.stream()
                .map(studioService::findOrCreateStudio)
                .collect(Collectors.toSet());
    }

    private Set<Demographic> saveDemographics(List<String> demographicNames) {
        return demographicNames.stream()
                .map(demographicService::findOrCreateDemographic)
                .collect(Collectors.toSet());
    }
}
