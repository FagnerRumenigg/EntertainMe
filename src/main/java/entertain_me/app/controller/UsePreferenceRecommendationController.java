package entertain_me.app.controller;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.RecommendationService;
import entertain_me.app.service.UserPreferenceRecommendationService;
import entertain_me.app.vo.AnimeVo;
import entertain_me.app.vo.ProblemVo;
import entertain_me.app.vo.UserPreferenceRecommendationVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "preferenceRecommendation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Preference Recommendation")
@CrossOrigin
@RestController
public class UsePreferenceRecommendationController {

    @Autowired
    UserPreferenceRecommendationService userPreferenceRecommendationService;

    @Operation(summary = "Get all information about the preferences recommendation", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demographics, Genres, Studios and Themes returned for the user choice",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnimeVo.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @PostMapping("/setupPreferenceRecommendation")
    public ResponseEntity<UserPreferenceRecommendationVo> getByGenre() throws Exception {
        return ResponseEntity.ok(userPreferenceRecommendationService.getPreferenceSetup());
    }
}
