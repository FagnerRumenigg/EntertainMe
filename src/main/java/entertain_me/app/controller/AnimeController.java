package entertain_me.app.controller;

import entertain_me.app.service.RecommendationService;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.ProblemVo;
import entertain_me.app.vo.AnimeVo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entertain_me.app.service.AnimeService;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "anime",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Anime")
@CrossOrigin
@RestController
public class AnimeController {

    @Autowired
    private AnimeService service;

    @Autowired
    private RecommendationService recommendationService;

    @Operation(summary = "Get anime by the title", method = "GET", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anime founded",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "Anime not founded",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "400", description = "Title is null or empty",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByTitle(@Parameter(description = "Anime title", example = "Naruto") @PathVariable String title)
            throws DataAccessResourceFailureException {
        Page<AllAnimeInfoVo> animeList = service.getAnimeByTitle(title);
        return animeList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);
    }
}
