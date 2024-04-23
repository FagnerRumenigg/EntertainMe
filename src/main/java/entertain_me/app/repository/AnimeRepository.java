package entertain_me.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entertain_me.app.model.Anime;
import entertain_me.app.vo.AnimeVO;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, String> {

  @Query("SELECT new entertain_me.app.vo.AnimeVO("
  		+ "a.titulo, a.fonteDeOrigem, a.situacaoAtual, a.sinopse, a.quantidadeEpisodios, a.anoLancamento, a.demografias, a.estudios, a.generos) "
  		+ "FROM Anime a WHERE a.titulo = :titulo")
  AnimeVO findAnimeByTitulo(String titulo);

}
