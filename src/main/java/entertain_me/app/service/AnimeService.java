package entertain_me.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entertain_me.app.repository.AnimeRepository;
import entertain_me.app.vo.AnimeVO;

@Service
public class AnimeService {

  @Autowired
  AnimeRepository repository;

  public AnimeVO getAnimeByTitulo(String titulo){
    try{
      throw new RuntimeException("Este é um exemplo de exceção!");
      //return repository.findAnimeByTitle(titulo);
    }catch(Exception e){
      throw e;
    }
  }

}