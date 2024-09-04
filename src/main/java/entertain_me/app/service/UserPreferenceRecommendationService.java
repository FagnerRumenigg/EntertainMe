package entertain_me.app.service;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.model.*;
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
            updateUserDemographics(user, preferencesDto.demographics(), "demographic");
        }
        if (!preferencesDto.genres().isEmpty()) {
            updateUserDemographics(user, preferencesDto.genres(), "genre");
        }
        if (!preferencesDto.studios().isEmpty()) {
            updateUserDemographics(user, preferencesDto.studios(), "studio");
        }
        if (!preferencesDto.themes().isEmpty()) {
            updateUserDemographics(user, preferencesDto.themes(), "theme");
        }

        userService.saveUser(user);
    }

    private void updateUserDemographics(User user, List<Long> ids, String typeRepository) {
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
