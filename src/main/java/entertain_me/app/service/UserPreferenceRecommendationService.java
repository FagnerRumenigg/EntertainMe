package entertain_me.app.service;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.dto.recommendation.RecommendationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import entertain_me.app.config.TokenServiceConfig;
import java.util.Collections;
import java.util.List;


@Service
public class UserPreferenceRecommendationService {

    @Autowired
    GenreService genreService;

    public List<RecommendationDto> recommendationListByGenre(){
        TokenServiceConfig.getUserIdFromContext();
        System.out.println(genreService.getGenreById(List.of(1L, 2L,3L ,4L)));

        return Collections.emptyList();
    }

}
