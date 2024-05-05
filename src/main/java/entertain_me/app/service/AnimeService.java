package entertain_me.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.AnimeRepository;
import entertain_me.app.vo.AnimeVO;

@Service
public class AnimeService {

  @Autowired
  AnimeRepository repository;

  public AnimeVO getAnimeByTitulo(String title){
    return repository.findAnimeByTitle(title);
  }

}