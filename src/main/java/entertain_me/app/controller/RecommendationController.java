package entertain_me.app.controller;

import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.RecommendationService;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.RecommendationListVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "recommend",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recommendation")
@CrossOrigin
@RestController
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;
    @GetMapping("getAnimeByDemographic")
    public ResponseEntity<Page<AllAnimeInfoVo>> meuOvo(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getByDemographic(paginationRequestDto));
    }

    @GetMapping("getAnimeByGenre")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByGenre(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getByGenre(paginationRequestDto));
    }

    @GetMapping("getAnimeByStudio")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByStudio(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getByStudio(paginationRequestDto));
    }

    @GetMapping("getAnimeByTheme")
    public ResponseEntity<Page<AllAnimeInfoVo>> getAnimeByTheme(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getByTheme(paginationRequestDto));
    }

    @GetMapping("upsideDownAnimes")
    public ResponseEntity<Page<AllAnimeInfoVo>> upsideDownAnimes(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.buildUpsideDownAnime(paginationRequestDto));
    }

    @GetMapping("favoriteEntertainMeTeam")
    public ResponseEntity<Page<AllAnimeInfoVo>> favoriteEntertainMeTeam(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getFavoriteEntertainMeTeam(paginationRequestDto));
    }

    @GetMapping("myAnimeListTopAnimes")
    public ResponseEntity<Page<AllAnimeInfoVo>> myAnimeListTopAnimes(@RequestBody PaginationRequestDto paginationRequestDto) throws Exception {
        return ResponseEntity.ok(recommendationService.getMyAnimeListTopAnimes(paginationRequestDto));
    }
}
