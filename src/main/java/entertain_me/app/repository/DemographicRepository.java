package entertain_me.app.repository;


import entertain_me.app.model.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DemographicRepository extends JpaRepository<Demographic, UUID> {

    Optional<Demographic> findByName(String name);
}
