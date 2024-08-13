package entertain_me.app.repository;

import entertain_me.app.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, UUID> {

    Optional<Studio> findByName(String name);
}
