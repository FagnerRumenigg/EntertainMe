package entertain_me.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.model.Anime;
import entertain_me.app.repository.AnimeRepository;

@Service
public class AnimeService {

  @Autowired
  AnimeRepository repository;

  public Anime getAnimeByTitulo(String titulo){
    return repository.findByTitulo(titulo);
  }

}