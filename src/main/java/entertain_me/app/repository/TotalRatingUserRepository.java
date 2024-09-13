package entertain_me.app.repository;

import entertain_me.app.model.TotalRatingUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalRatingUserRepository extends JpaRepository<TotalRatingUsers, Long> {
}
