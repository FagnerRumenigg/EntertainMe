package entertain_me.app.dto.recommendation;

public record UserInteractionDto(
    Long idAnime,
    Short rating,
    Boolean noInterest,
    Boolean watched) {
}
