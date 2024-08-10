package entertain_me.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.AnimeRepository;
import entertain_me.app.vo.AnimeVo;

import java.util.List;

@Log4j2
@Service
public class AnimeService {

  @Autowired
  AnimeRepository repository;

  public List<AnimeVo> getAnimeByTitle(String title){
    if(title == null || title.trim().isEmpty()){
      log.warn("Title is empty");
      throw new IllegalArgumentException("Title is empty");
    }
    return repository.findByTitleContainingIgnoreCase(title);
  }
}