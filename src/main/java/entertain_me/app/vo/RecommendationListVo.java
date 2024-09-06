package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecommendationListVo(
        @Schema(description = "List with the demographics",
                example = "[{demographicId: 1, name:name}]")
        List<AnimeVo> animesByDemographics,
        @Schema(description = "List with the genres",
                example = "[{genreId: 1, name:name}]")
        List<AnimeVo> animesByGenres,
        @Schema(description = "List with the studios",
                example = "[{studioId: 1, name:name}]")
        List<AnimeVo> animesByStudios,
        @Schema(description = "List with the themes",
                example = "[{themeId: 1, name:name}]")
        List<AnimeVo> animesByThemes,

        List<AllAnimeInfoVo> entertainMeFavoriteAnimes,

        List<AllAnimeInfoVo> topAnimes,

        List<AnimeVo> upsideDownWorldAnimes
) {

}
