package entertain_me.app.controller;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.RecommendationService;
import entertain_me.app.service.UserPreferenceRecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "preferenceRecommendation",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Preference Recommendation")
@CrossOrigin
@RestController
public class UsePreferenceRecommendationController {

    @Autowired
    UserPreferenceRecommendationService userPreferenceRecommendationService;

    @PostMapping("/getByGenre")
    public void getByGenre() throws Exception {
        userPreferenceRecommendationService.recommendationListByGenre();
    }
}
