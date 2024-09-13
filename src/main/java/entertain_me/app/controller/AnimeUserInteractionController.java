package entertain_me.app.controller;

import com.azure.core.annotation.Post;
import entertain_me.app.dto.recommendation.UserInteractionDto;
import entertain_me.app.service.AnimeUserInteractionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "interaction",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AnimeUserInteraction")
@CrossOrigin
@RestController
public class AnimeUserInteractionController {

    @Autowired
    AnimeUserInteractionService animeUserInteractionService;
    @Post("/save")
    public void saveAnimeUserInteraction(UserInteractionDto animeUserInteractions){
        animeUserInteractionService.saveUserInteraction(animeUserInteractions);
    }
}
