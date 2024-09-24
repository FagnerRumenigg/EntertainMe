package entertain_me.app.dto;

public record TranslateInfoDto(
        Long animeId,
        String q,
        String source,
        String target
) {
}
