package entertain_me.app.controller;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.UserPreferenceRecommendationService;
import entertain_me.app.vo.ProblemVo;
import entertain_me.app.vo.UserPreferenceRecommendationVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Save the recommendation preference for the user", method = "POST", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferences saved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePreferences(@RequestBody PreferencesDto preferencesDto){
        userPreferenceRecommendationService.savePreferences(preferencesDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all the preferences that the user can choose",
            method = "GET",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserPreferenceRecommendationVo.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPreferencesRecommendation() {
        return ResponseEntity.ok(userPreferenceRecommendationService.getAllPreferencesRecommendation());
    }
}
