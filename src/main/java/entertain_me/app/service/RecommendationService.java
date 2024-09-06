package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.AnimeVo;
import entertain_me.app.vo.RecommendationListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RecommendationService {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    public RecommendationListVo getListOfPreferenceRecommendation() {
        Long userId = TokenServiceConfig.getUserIdFromContext();

        PreferencesDto preferencesDto = mountUserPreferences(userId);

        List<AnimeVo> animesByDemographic = animeService.getAnimeByDemographic(preferencesDto.demographics());
        List<AnimeVo> animesByGenre = animeService.getAnimeByGenre(preferencesDto.genres());
        List<AnimeVo> animesByStudio = animeService.getAnimeByStudio(preferencesDto.studios());
        List<AnimeVo> animesByTheme = animeService.getAnimeByTheme(preferencesDto.themes());

        return new RecommendationListVo(
                animesByDemographic, animesByGenre, animesByStudio, animesByTheme, null, null, null
        );
    }

    private PreferencesDto mountUserPreferences(Long userId) {
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

}
