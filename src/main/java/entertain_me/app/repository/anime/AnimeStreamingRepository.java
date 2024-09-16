package entertain_me.app.repository.anime;


import entertain_me.app.model.Anime.AnimeStreaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeStreamingRepository extends JpaRepository <AnimeStreaming, Long> {
}
