package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.AllAnimeInfoVoUnique;
import entertain_me.app.vo.AnimeVo;
import entertain_me.app.vo.RecommendationListVo;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
public class RecommendationService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JikanService jikanService;
    @Value("${anime.favorites}")
    private String favoriteAnimeIdsString;
    private List<Long> favoriteAnimeIds;
    public RecommendationListVo getHomeListByUser() throws Exception {

        Long userId = TokenServiceConfig.getUserIdFromContext();

        PreferencesDto preferencesDto = buildUserPreferences(userId);
        log.info("BATEU AQUI");
        List<AllAnimeInfoVo> animesByDemographic = animeService.getAnimeByDemographic(preferencesDto.demographics());
        List<AllAnimeInfoVo> animesByGenre = animeService.getAnimeByGenre(preferencesDto.genres());
        List<AllAnimeInfoVo> animesByStudio = animeService.getAnimeByStudio(preferencesDto.studios());
        List<AllAnimeInfoVo> animesByTheme = animeService.getAnimeByTheme(preferencesDto.themes());



        return new RecommendationListVo(
                animesByDemographic,
                animesByGenre,
                animesByStudio,
                animesByTheme,
                animeService.getEntertainMeTeamFavoriteAnimes(favoriteAnimeIds),
                jikanService.getTopAnimesJikan(),
                buildUpsideDownAnime(preferencesDto)
        );
    }

    private PreferencesDto buildUserPreferences(Long userId) {
        Set<Object> preferences = userService.getUserPreferences(userId);

        List<Long> demographicsIds = new ArrayList<>();
        List<Long> genreIds = new ArrayList<>();
        List<Long> studioIds = new ArrayList<>();
        List<Long> themeIds = new ArrayList<>();

        for (Object preference : preferences) {
            if (preference instanceof Demographic) {
                demographicsIds.add(((Demographic) preference).getIdDemographic());
            } else if (preference instanceof Genre) {
                genreIds.add(((Genre) preference).getIdGenre());
            } else if (preference instanceof Studio) {
                studioIds.add(((Studio) preference).getIdStudio());
            } else if (preference instanceof Theme) {
                themeIds.add(((Theme) preference).getIdTheme());
            }
        }

        return new PreferencesDto(
                demographicsIds,
                genreIds,
                studioIds,
                themeIds
        );
    }

    private List<AllAnimeInfoVo> buildUpsideDownAnime(PreferencesDto preferencesDto){
        List<AllAnimeInfoVo> upsideDownListAnime = new ArrayList<>();

        List<AllAnimeInfoVo> animesByDemographic = animeService.getAnimeByOtherDemographic(preferencesDto.demographics());
        List<AllAnimeInfoVo> animesByGenre = animeService.getAnimeByOtherGenre(preferencesDto.genres());
        List<AllAnimeInfoVo> animesByStudio = animeService.getAnimeByOtherStudio(preferencesDto.studios());
        List<AllAnimeInfoVo> animesByTheme = animeService.getAnimeByOtherTheme(preferencesDto.themes());

        upsideDownListAnime.addAll(animesByDemographic);
        upsideDownListAnime.addAll(animesByGenre);
        upsideDownListAnime.addAll(animesByStudio);
        upsideDownListAnime.addAll(animesByTheme);

        return upsideDownListAnime;
    }

    @PostConstruct
    public void init() {
        favoriteAnimeIds = Arrays.stream(favoriteAnimeIdsString.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
