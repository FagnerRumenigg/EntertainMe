package entertain_me.app.repository;

import entertain_me.app.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {
}
