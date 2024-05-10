package entertain_me.app.dto.jikan_api;

import java.util.List;

public record JikanRequestAllDto(
        Integer mal_id,
        String url,
        ImageUrlsDto images,
        TrailerDto trailerDto,
        boolean approved,
        List<TitleDto> titleDtos,
        String title,
        String title_english,
        String title_japanese,
        List<String> title_synonyms,
        String type,
        String source,
        Integer episodes,
        String status,
        boolean airing,
        AiredDto aired,
        String duration,
        double score,
        Integer scored_by,
        Integer rank,
        Integer popularity,
        Integer members,
        Integer favorites,
        String synopsis,
        String background,
        String season,
        Integer year,
        BroadcastDto broadcast,
        List<ProducerDto> producers,
        List<LicensorDto> licensors,
        List<StudioDto> studios,
        List<GenreDto> genres,
        List<GenreDto> explicit_genres,
        List<GenreDto> themes,
        List<DemographicsDto> demographics
) {
    // Construtor personalizado

    public JikanRequestAllDto(String title, String synopsis) {
        this(null, null, null, null, false, null, title, null, null, null, null,
                null, null, null, false, null, null, 0, null, null,
                null, null, null, synopsis, null, null, null, null,
                null, null, null, null, null, null, null);
    }
}
