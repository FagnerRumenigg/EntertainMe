package entertain_me.app.controller;

import com.azure.core.annotation.Get;
import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.RecommendationService;
import entertain_me.app.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "recommend",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recommendation")
@CrossOrigin
@RestController
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @Operation(summary = "Get anime by demographic", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animes by demographic",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("getAnimeByDemographic")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByDemographic(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getByDemographic(page, size);
        return animeList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get anime by genres", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animes by genres",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("getAnimeByGenre")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByGenre(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getByGenre(page, size);

        return animeList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get anime by Studio", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animes by studios",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("getAnimeByStudio")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByStudio(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getByStudio(page, size);

        return animeList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get anime by theme", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animes by themes",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("getAnimeByTheme")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByTheme(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getByTheme(page, size);

        return animeList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get a list of animes that is the most distinct for the user", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of animes that are the most distinct from the user preference ",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("upsideDownAnimes")
    public ResponseEntity<Page<AllAnimeInfoVo>> upsideDownAnimes(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.buildUpsideDownAnime(page, size);

        return animeList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get a list of favorite animes from the entertainMe team", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of favorite animes from the entertainMe team ",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("favoriteEntertainMeTeam")
    public ResponseEntity<Page<AllAnimeInfoVo>> favoriteEntertainMeTeam(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getFavoriteEntertainMeTeam(page, size);

        return animeList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @Operation(summary = "Get a list of the top animes from myAnimeList", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of top animes from the myAnimeList ",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Preference not found",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("myAnimeListTopAnimes")
    public ResponseEntity<Page<AllAnimeInfoVo>> myAnimeListTopAnimes(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Page<AllAnimeInfoVo> animeList = recommendationService.getMyAnimeListTopAnimes(page, size);

        return animeList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }

    @GetMapping("recommendationMasterBlasterTop")
    public ResponseEntity<AllAnimeInfoVo> recommendationMasterBlasterTop(){
        recommendationService.getRecommendationMasterBlasterTop();
        return ResponseEntity.ok().build();
    }

    @GetMapping("seasonNow")
    public ResponseEntity<Page<JikanSeasonNowVo>> animesSeasonNow(
        @RequestParam int page,
        @RequestParam int size) throws Exception {
        Page<JikanSeasonNowVo> jikanSeasonNowVoList = recommendationService.getSeasonNowJikan(page, size);
        return jikanSeasonNowVoList.isEmpty() ?  ResponseEntity.noContent().build() : ResponseEntity.ok(jikanSeasonNowVoList);

    }
}