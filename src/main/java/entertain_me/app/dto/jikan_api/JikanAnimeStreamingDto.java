package entertain_me.app.dto.jikan_api;

import lombok.ToString;

public record JikanAnimeStreamingDto(
        Long animeId,
        String name,
        String url
) {
}
