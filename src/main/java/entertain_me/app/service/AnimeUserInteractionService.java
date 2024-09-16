package entertain_me.app.service;

import com.azure.core.annotation.Get;
import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.anime.AnimeUserInteractionDto;
import entertain_me.app.dto.recommendation.UserInteractionDto;
import entertain_me.app.model.TotalRatingUsers;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractionId;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractions;
import entertain_me.app.repository.AnimeUserInteractionRepository;
import entertain_me.app.repository.TotalRatingUserRepository;
import entertain_me.app.vo.AllAnimeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnimeUserInteractionService {

    @Autowired
    AnimeUserInteractionRepository animeUserInteractionRepository;

    @Autowired
    TotalRatingUserRepository totalRatingUserRepository;

    public void saveUserInteraction(UserInteractionDto userInteractionDto){
        AnimeUserInteractionId userInteractionId = new AnimeUserInteractionId();
        AnimeUserInteractions userInteraction = new AnimeUserInteractions();

        userInteractionId.setIdUser(TokenServiceConfig.getUserIdFromContext());
        userInteractionId.setIdAnime(userInteractionDto.idAnime());

        userInteraction.setId(userInteractionId);
        userInteraction.setRating(userInteractionDto.rating());
        userInteraction.setNoInterest(userInteractionDto.noInterest());
        userInteraction.setWatched(userInteractionDto.watched());
        userInteraction.setInteractionDate(LocalDateTime.now());

        animeUserInteractionRepository.save(userInteraction);

        TotalRatingUsers existingTotalRating = totalRatingUserRepository.findById(userInteractionDto.idAnime())
                .orElse(new TotalRatingUsers(
                        userInteractionDto.idAnime(),
                        0.0,
                        0,
                        null));

        Double newTotalRating = existingTotalRating.getTotalRating() + userInteractionDto.rating();
        Integer newTotalVotes = existingTotalRating.getTotalVotes() + 1;

        TotalRatingUsers updatedTotalRating = new TotalRatingUsers(
                existingTotalRating.getId(),
                newTotalRating,
                newTotalVotes,
                LocalDateTime.now());

        totalRatingUserRepository.save(updatedTotalRating);
    }

}
