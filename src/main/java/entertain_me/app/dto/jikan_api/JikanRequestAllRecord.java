package entertain_me.app.dto.jikan_api;

import java.util.List;

public record JikanRequestAllRecord(
        Integer mal_id,
        String url,
        ImageUrls images,
        Trailer trailer,
        boolean approved,
        List<Title> titles,
        String title,
        String title_english,
        String title_japanese,
        List<String> title_synonyms,
        String type,
        String source,
        Integer episodes,
        String status,
        boolean airing,
        Aired aired,
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
        Broadcast broadcast,
        List<Producer> producers,
        List<Licensor> licensors,
        List<Studio> studios,
        List<Genre> genres,
        List<Genre> explicit_genres,
        List<Genre> themes,
        List<Demographics> demographics
) {
    // Construtor personalizado

    public JikanRequestAllRecord(String title, String synopsis) {
        this(null, null, null, null, false, null, title, null, null, null, null,
                null, null, null, false, null, null, 0, null, null,
                null, null, null, synopsis, null, null, null, null,
                null, null, null, null, null, null, null);
    }
}
