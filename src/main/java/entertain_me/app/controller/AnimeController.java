package entertain_me.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.service.AnimeService;

@CrossOrigin
@RequestMapping("anime")
@RestController
public class AnimeController {

	@Autowired
	private AnimeService service;

	private Logger logger = LoggerFactory.getLogger(AnimeController.class);

  @GetMapping("/getByTitulo/{titulo}")
  public ResponseEntity<?> getAnimeByTitulo(@PathVariable String titulo) {
      try{
          return ResponseEntity.ok(service.getAnimeByTitulo(titulo));
      } catch(Exception e){
          logger.error("Error descriptrion: ", e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
  }
}
