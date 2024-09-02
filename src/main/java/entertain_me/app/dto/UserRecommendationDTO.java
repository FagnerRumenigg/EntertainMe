package entertain_me.app.dto;

import java.util.UUID;

public record UserRecommendationDTO(
        UUID userId,
        int animeId,
        double rating){

}