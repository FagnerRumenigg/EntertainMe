package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecommendationListVo(
        @Schema(description = "List with the demographics",
                example = "[{demographicId: 1, name:name}]")
        List<AllAnimeInfoVo> animesByDemographics,
        @Schema(description = "List with the genres",
                example = "[{genreId: 1, name:name}]")
        List<AllAnimeInfoVo> animesByGenres,
        @Schema(description = "List with the studios",
                example = "[{studioId: 1, name:name}]")
        List<AllAnimeInfoVo> animesByStudios,
        @Schema(description = "List with the themes",
                example = "[{themeId: 1, name:name}]")
        List<AllAnimeInfoVo> animesByThemes,

        List<AllAnimeInfoVo> entertainMeFavoriteAnimes,

        List<AllAnimeInfoVo> topAnimes,

        List<AllAnimeInfoVo> upsideDownWorldAnimes
) {

}
