package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
import entertain_me.app.vo.UserPreferenceRecommendationVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service public class UserPreferenceRecommendationService {

    @Autowired
    UserService userService;

    @Autowired
    DemographicService demographicService;

    @Autowired
    GenreService genreService;

    @Autowired
    StudioService studioService;

    @Autowired
    ThemeService themeService;

    @Transactional
    public void savePreferences(PreferencesDto preferencesDto) {
        User user = userService.getById(TokenServiceConfig.getUserIdFromContext());

        if (!preferencesDto.demographics().isEmpty()) {
            updateUserPreferenceRecommendation(user, preferencesDto.demographics(), "demographic");
        }
        if (!preferencesDto.genres().isEmpty()) {
            updateUserPreferenceRecommendation(user, preferencesDto.genres(), "genre");
        }
        if (!preferencesDto.studios().isEmpty()) {
            updateUserPreferenceRecommendation(user, preferencesDto.studios(), "studio");
        }
        if (!preferencesDto.themes().isEmpty()) {
            updateUserPreferenceRecommendation(user, preferencesDto.themes(), "theme");
        }

        userService.saveUser(user);
    }

    public UserPreferenceRecommendationVo getAllPreferencesRecommendation(){
        return new UserPreferenceRecommendationVo(
                demographicService.findAllDemographics(),
                genreService.findAllGenre(),
                studioService.findAllStudio(),
                themeService.findAllTheme()
        );
    }

    private void updateUserPreferenceRecommendation(User user, List<Long> ids, String typeRepository) {
        switch (typeRepository) {
            case "demographic":
                Set<Demographic> demographics = demographicService.findAllDemographicsById(ids);
                user.setDemographics(demographics);
                break;
            case "genre":
                Set<Genre> genres = genreService.findAllGenreById(ids);
                user.setGenres(genres);
                break;
            case "studio":
                Set<Studio> studios = studioService.finAllStudioById(ids);
                user.setStudios(studios);
                break;
            case "theme":
                Set<Theme> themes = themeService.findAllThemesById(ids);
                user.setThemes(themes);
                break;
        }
    }

}
