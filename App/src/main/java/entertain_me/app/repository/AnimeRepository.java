package entertain_me.app.repository;

import entertain_me.app.model.Anime;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {

    Anime findByTitulo(String titulo);
}
