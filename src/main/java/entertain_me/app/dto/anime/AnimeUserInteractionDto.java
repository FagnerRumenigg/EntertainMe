package entertain_me.app.dto.anime;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record AnimeUserInteractionDto(
    long idAnime,
    short rating,
    boolean noInterest,
    boolean watched) {
}
