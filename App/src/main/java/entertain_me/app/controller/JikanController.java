package entertain_me.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.service.JikanService;

@RequestMapping("jikan-api")
@RestController()
public class JikanController {

    private final JikanService animeReturnService;

    private final Logger logger = LoggerFactory.getLogger(JikanController.class);

    public JikanController(JikanService animeReturnService) {
        this.animeReturnService = animeReturnService;
    }

    @GetMapping("/atualizar-database")
    public ResponseEntity<?> getAllAnimes() {
        try{
            animeReturnService.getAllAnimesJikan();

            return ResponseEntity.ok().build();
        } catch(Exception e){
            logger.error("Error descriptrion: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getNew/{jikanId}")
    public ResponseEntity<String> getAnimeNews(@PathVariable Integer jikanId) {
        try{
            String animeNews = animeReturnService.getAnimeNews(jikanId);

            return ResponseEntity.ok(animeNews);
        } catch(Exception e){
            logger.error("Error descriptrion: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
