package entertain_me.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.AnimeRepository;
import entertain_me.app.vo.AnimeVo;

import java.util.List;

@Service
public class AnimeService {

  @Autowired
  AnimeRepository repository;

  public List<AnimeVo> getAnimeByTitle(String title){
    return repository.findByTitleContainingIgnoreCase(title);
  }
}