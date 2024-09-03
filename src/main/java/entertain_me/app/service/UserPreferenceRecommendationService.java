package entertain_me.app.service;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.dto.recommendation.RecommendationDto;
import entertain_me.app.vo.UserPreferenceRecommendationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import entertain_me.app.config.TokenServiceConfig;
import java.util.Collections;
import java.util.List;


@Service
public class UserPreferenceRecommendationService {

    @Autowired
    DemographicService demographicService;

    @Autowired
    GenreService genreService;

    @Autowired
    StudioService studioService;

    @Autowired
    ThemeService themeService;


    public UserPreferenceRecommendationVo getPreferenceSetup(){

        return new UserPreferenceRecommendationVo(
                demographicService.findAllDemographics(),
                genreService.findAllGenre(),
                studioService.findAllStudio(),
                themeService.findAllTheme()
        );
    }

}
