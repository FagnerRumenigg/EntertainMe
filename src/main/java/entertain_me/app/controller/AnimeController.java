package entertain_me.app.controller;

import entertain_me.app.vo.AnimeVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.service.AnimeService;

@CrossOrigin
@RestController
@RequestMapping(value = "anime", produces = {"application/json"})
@Tag(name = "Anime")
public class AnimeController {

    @Autowired
    private AnimeService service;

    @Operation(summary = "Get anime by the title", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anime founded",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeVO.class))}),
            @ApiResponse(responseCode = "404", description = "Anime not founded"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })

    @GetMapping(value = "/getByTitle/{title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAnimeByTitle(@Parameter(description = "Anime title", example = "Naruto") @PathVariable String title) {
        return ResponseEntity.ok(service.getAnimeByTitulo(title));
    }
}
