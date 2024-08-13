package entertain_me.app.repository;


import entertain_me.app.model.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DemographicRepository extends JpaRepository<Demographic, UUID> {
}
