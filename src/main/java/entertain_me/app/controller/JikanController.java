package entertain_me.app.controller;

import entertain_me.app.vo.exception.ProblemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.service.JikanService;

@RequestMapping(value = "jikan-api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Jikan")
@CrossOrigin
@RestController()

public class JikanController {

    private final JikanService animeReturnService;


    public JikanController(JikanService animeReturnService) {
        this.animeReturnService = animeReturnService;
    }

    @Operation(summary = "Update the database completely", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Database updated"),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
    @GetMapping(value = "/update-database")
    public ResponseEntity<?> getAllAnimes() throws Exception {
        animeReturnService.getAllAnimesJikan();
        return ResponseEntity.ok().build();
    }
}
