package entertain_me.app.service;

import entertain_me.app.dto.anime.JikanAnimeIdsDto;
import entertain_me.app.dto.jikan_api.JikanAnimeStreamingDto;
import entertain_me.app.dto.jikan_api.JikanResponseDataDto;
import entertain_me.app.model.*;
import entertain_me.app.model.Anime.Anime;
import entertain_me.app.repository.anime.AnimeRepository;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.JikanSeasonNowVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JikanService {

    @Autowired
    private JikanAPIService jikanAPIService;
    @Autowired
    private AnimeService animeService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private StudioService studioService;
    @Autowired
    private DemographicService demographicService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private AnimeImageService animeImageService;

    @Autowired
    private AnimeStreamingService animeStreamingService;


    public void getAllAnimesJikan() throws Exception {
        try {
            int page = 1;
            boolean returnOk = true;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime timeStart = LocalDateTime.now();

            while (returnOk) {
                List<JikanResponseDataDto> animesList = jikanAPIService.requestAllAnimes(page);
                log.info("Returned {} animes at the page: {}", animesList.size(), page);

                if (!animesList.isEmpty()) {
                    for (JikanResponseDataDto anime : animesList) {
                        if(animeService.getAnimeByJikanId(anime.jikanId()).isEmpty()){
                            Anime newAnime = animeService.setAnimeFromJikan(anime);
                            animeImageService.saveImages(newAnime.getId() ,anime.imageUrl(), anime.smallImageUrl(), anime.largeImageUrl());

                            log.info("Anime {} registered ",anime.title());
                        }else{
                            log.info("Anime {} already registered", anime.title());
                        }

                    }
                } else {
                    returnOk = false;
                }

                Duration difference = Duration.between(timeStart, LocalDateTime.now());
                long passedMinutes = difference.toMinutes();

                if (passedMinutes >= 5) {
                    timeStart = LocalDateTime.now();
                    log.info("5 minutes passed, page: {} - {}", page, timeStart.format(format));
                }
                page++;
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            log.error("Fail updating anime database", e);
            throw new Exception("Fail updating anime database", e);
        }
    }

    public Page<AllAnimeInfoVo> getTopAnimesJikan(Pageable pageable) throws Exception {
        List<JikanResponseDataDto> animesList = jikanAPIService.requestTopAnimes();

        List<AllAnimeInfoVo> allAnimesInfoVo = new ArrayList<>();
        animesList.forEach((anime) -> {
            AllAnimeInfoVo allAnimeInfoVo = new AllAnimeInfoVo(
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
                    anime.themesName()
            );
            allAnimesInfoVo.add(allAnimeInfoVo);
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allAnimesInfoVo.size());
        List<AllAnimeInfoVo> pagedAnimes = allAnimesInfoVo.subList(start, end);

        return new PageImpl<>(pagedAnimes, pageable, allAnimesInfoVo.size());
    }

    public Page<JikanSeasonNowVo> getSeasonNowJikan(Pageable pageable, Integer limitPerPage) throws Exception{
        List<JikanSeasonNowVo> jikanSeasonNowVo = jikanAPIService.requestSeasonAnimes(limitPerPage);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), jikanSeasonNowVo.size());
        List<JikanSeasonNowVo> pagedAnimes = jikanSeasonNowVo.subList(start, end);

        return new PageImpl<>(pagedAnimes, pageable, jikanSeasonNowVo.size());

    }

    public void setAnimeStreaming(List<JikanAnimeIdsDto> jikanId) throws Exception{
        for (JikanAnimeIdsDto jikanAnimeIdsDto : jikanId) {
            List<JikanAnimeStreamingDto> streamingDtoList = jikanAPIService.requestAnimeStreaming(jikanAnimeIdsDto);

            animeStreamingService.save(streamingDtoList);
            log.info("Anime {} processed. {}", jikanAnimeIdsDto.id(), streamingDtoList);

            Thread.sleep(1500);
        }
    }
}
