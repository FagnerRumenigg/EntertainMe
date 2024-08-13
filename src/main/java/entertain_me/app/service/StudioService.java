package entertain_me.app.service;

import entertain_me.app.model.Demographic;
import entertain_me.app.model.Studio;
import entertain_me.app.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudioService {

    @Autowired
    StudioRepository studioRepository;

    public Studio findOrCreateStudio(String name) {
        return studioRepository.findByName(name)
                .orElseGet(() -> studioRepository.save(new Studio(UUID.randomUUID(), name)));
    }
}
