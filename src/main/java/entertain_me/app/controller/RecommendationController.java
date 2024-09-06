package entertain_me.app.controller;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.service.RecommendationService;
import entertain_me.app.vo.RecommendationListVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("homeListAnimes")
    public ResponseEntity<RecommendationListVo> meuOvo(){
        return ResponseEntity.ok(recommendationService.getListOfPreferenceRecommendation());
    }
}
