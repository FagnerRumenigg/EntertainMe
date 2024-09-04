package entertain_me.app.service;

import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import entertain_me.app.model.*;
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
    private final ThemeService themeService;

    @Autowired
    public JikanService(JikanAPIService jikanAPIService, AnimeRepository repository, GenreService genreService,
                        StudioService studioService, DemographicService demographicService, ThemeService themeService) {
        this.jikanAPIService = jikanAPIService;
        this.repository = repository;
        this.genreService = genreService;
        this.studioService = studioService;
        this.demographicService = demographicService;
        this.themeService = themeService;

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
                    List<JikanResponseDataDto> animesReturn = animesList.stream()
                            .map(anime -> new JikanResponseDataDto(
                                    anime.jikanId(),
                                    anime.title(),
                                    anime.source(),
                                    anime.status(),
                                    anime.ageRating(),
                                    anime.synopsis(),
                                    anime.episodes(),
                                    anime.year(),
                                    anime.demographicsName(),
                                    anime.studiosName(),
                                    anime.genresName(),
                                    anime.themesName())
                            ).toList();
                    for (JikanResponseDataDto anime : animesReturn) {
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
            log.error("Fail updating anime database", e);
            throw new Exception("Fail updating anime database", e);
        }
    }

    private Anime setAnimeFromJikan(JikanResponseDataDto anime) {
        Anime animeNovo = new Anime();

        Set<Genre> genres = saveGenres(anime.genresName());
        Set<Studio> studios = saveStudios(anime.studiosName());
        Set<Demographic> demographics = saveDemographics(anime.demographicsName());
        Set<Theme> themes = saveTheme(anime.themesName());

        animeNovo.setJikanId(anime.jikanId());
        animeNovo.setTitle(anime.title());
        animeNovo.setSource(anime.source());
        animeNovo.setStatus(anime.status());
        animeNovo.setAgeRating(anime.ageRating());
        animeNovo.setSynopsys(anime.synopsis());
        animeNovo.setEpisodes(anime.episodes());
        animeNovo.setYear(anime.year());
        animeNovo.setGenres(genres);
        animeNovo.setStudios(studios);
        animeNovo.setDemographics(demographics);
        animeNovo.setThemes(themes);

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

    private Set<Theme> saveTheme(List<String> themeNames) {
        return themeNames.stream()
                .map(themeService::findOrCreateTheme)
                .collect(Collectors.toSet());
    }
}
