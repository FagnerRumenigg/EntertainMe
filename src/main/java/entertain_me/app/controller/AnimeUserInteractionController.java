package entertain_me.app.controller;

import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.recommendation.UserInteractionDto;
import entertain_me.app.service.AnimeUserInteractionService;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.ProblemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "interaction",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AnimeUserInteraction")
@CrossOrigin
@RestController
public class AnimeUserInteractionController {

    @Autowired
    AnimeUserInteractionService animeUserInteractionService;

    @Operation(summary = "Save user interaction", method = "POST", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interactions saved",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @PostMapping("/save")
    public void saveAnimeUserInteraction(@RequestBody UserInteractionDto animeUserInteractions){
        animeUserInteractionService.saveUserInteraction(animeUserInteractions);
    }

    @Operation(summary = "Get anime's watching", method = "GET", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of anime's watching",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AllAnimeInfoVo.class))}),
            @ApiResponse(responseCode = "204", description = "List is empty",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("/getAnimesIsWatching")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimesInMyList (PaginationRequestDto paginationRequestDto){
        Page<AllAnimeInfoVo> animeList = animeUserInteractionService.getAnimeIsWatching(paginationRequestDto);
        return animeList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animeList);    }
}
