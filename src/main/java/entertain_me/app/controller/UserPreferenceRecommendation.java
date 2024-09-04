package entertain_me.app.controller;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.UserPreferenceRecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "userPreferenceRecommendation",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "UserPreferenceRecommendation")
@CrossOrigin
@RestController
public class UserPreferenceRecommendation {

    @Autowired
    UserPreferenceRecommendationService userPreferenceRecommendationService;

    @PostMapping("/save")
    public ResponseEntity<?> savePreferences(@RequestBody PreferencesDto preferencesDto){
        userPreferenceRecommendationService.savePreferences(preferencesDto);
        return ResponseEntity.ok().build();
    }
}
