package entertain_me.app.repository;

import entertain_me.app.model.Anime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, String> {

    Anime findByTitulo(String titulo);
}
