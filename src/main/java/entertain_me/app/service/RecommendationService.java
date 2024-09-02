package entertain_me.app.service;

import entertain_me.app.repository.AnimeUserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecommendationService {

    @Autowired
    private AnimeUserInteractionRepository animeUserInteractionRepository;

    @Autowired
    private AnimeService animeService;

}
