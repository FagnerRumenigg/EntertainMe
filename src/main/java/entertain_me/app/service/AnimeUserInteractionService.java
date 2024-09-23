package entertain_me.app.service;

import com.azure.core.annotation.Get;
import entertain_me.app.component.Helper;
import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.dto.PaginationRequestDto;
import entertain_me.app.dto.anime.AnimeUserInteractionDto;
import entertain_me.app.dto.recommendation.UserInteractionDto;
import entertain_me.app.model.TotalRatingUsers;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractionId;
import entertain_me.app.model.animeUserInteraction.AnimeUserInteractions;
import entertain_me.app.repository.AnimeUserInteractionRepository;
import entertain_me.app.repository.TotalRatingUserRepository;
import entertain_me.app.vo.AllAnimeInfoVo;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class AnimeUserInteractionService {

    @Autowired
    AnimeUserInteractionRepository animeUserInteractionRepository;

    @Autowired
    TotalRatingUserRepository totalRatingUserRepository;

    @Autowired
    AnimeService animeService;

    @Autowired
    Helper helper;

    public void saveUserInteraction(UserInteractionDto userInteractionDto){
        AnimeUserInteractionId userInteractionId = new AnimeUserInteractionId();
        AnimeUserInteractions userInteraction = new AnimeUserInteractions();

        userInteractionId.setIdUser(TokenServiceConfig.getUserIdFromContext());
        userInteractionId.setIdAnime(userInteractionDto.idAnime());

        userInteraction.setId(userInteractionId);
        userInteraction.setRating(userInteractionDto.rating());
        userInteraction.setNoInterest(userInteractionDto.noInterest());
        userInteraction.setWatched(userInteractionDto.isWatched());
        userInteraction.setMyList(userInteractionDto.myList());
        userInteraction.setWatching(userInteractionDto.isWatching());
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

    public Page<AllAnimeInfoVo> getAnimeIsWatching(PaginationRequestDto paginationRequestDto){
        Pageable pageable = helper.createPageable(paginationRequestDto);
        Long userId = TokenServiceConfig.getUserIdFromContext();
        List<Long> animesId =  animeUserInteractionRepository.findInteractionsByUserIdAndMyList(userId);
        List<AllAnimeInfoVo> animesList = animeService.getAnimesById(animesId);

        return new PageImpl<>(animesList, helper.createPageable
                (paginationRequestDto), animesList.size());

    }
}
