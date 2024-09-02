package entertain_me.app.repository;

import entertain_me.app.model.AnimeUserInteractions;
import entertain_me.app.model.AnimeUserInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeUserInteractionRepository extends JpaRepository<AnimeUserInteractions, AnimeUserInteractionId> {

}
