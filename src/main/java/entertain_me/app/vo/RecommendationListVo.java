package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

public record RecommendationListVo(
        @Schema(description = "List with the demographics",
                example = "[{demographicId: 1, name:name}]")
        Page<AllAnimeInfoVo> animesByDemographics,
        @Schema(description = "List with the genres",
                example = "[{genreId: 1, name:name}]")
        Page<AllAnimeInfoVo> animesByGenres,
        @Schema(description = "List with the studios",
                example = "[{studioId: 1, name:name}]")
        Page<AllAnimeInfoVo> animesByStudios,
        @Schema(description = "List with the themes",
                example = "[{themeId: 1, name:name}]")
        Page<AllAnimeInfoVo> animesByThemes,

        Page<AllAnimeInfoVo> entertainMeFavoriteAnimes,

        Page<AllAnimeInfoVo> topAnimes,

        Page<AllAnimeInfoVo> upsideDownWorldAnimes
) {

}
