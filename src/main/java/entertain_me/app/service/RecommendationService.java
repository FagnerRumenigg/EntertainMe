package entertain_me.app.service;

import entertain_me.app.dto.recommendation.PreferencesDto;
import entertain_me.app.dto.recommendation.RecommendationDto;
import entertain_me.app.repository.AnimeUserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class RecommendationService {

    @Autowired
    private AnimeUserInteractionRepository animeUserInteractionRepository;

    @Autowired
    private AnimeService animeService;

    @Autowired
    private GenreService genreService;

}
