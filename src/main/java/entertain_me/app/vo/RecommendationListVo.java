package entertain_me.app.vo;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;
import entertain_me.app.model.Anime;
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

        List<AnimeVo> topAnimes,

        List<AnimeVo> upsideDownWorldAnimes
) {

}
