package entertain_me.app.repository.anime;

import entertain_me.app.model.Anime.AnimeImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeImagesRepository extends JpaRepository <AnimeImages, Long> {
}
