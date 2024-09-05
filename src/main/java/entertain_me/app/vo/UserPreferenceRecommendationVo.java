package entertain_me.app.vo;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "UserPreferenceRecommendationVo",
        description = "This VO is used to return the recommendation preferences")
public record UserPreferenceRecommendationVo(
        @Schema(description = "List with the demographics",
                example = "[{demographicId: 1, name:name}]")
        List<DemographicDto> demographics,
        @Schema(description = "List with the genres",
                example = "[{genreId: 1, name:name}]")
        List<GenreDto> genres,
        @Schema(description = "List with the studios",
                example = "[{studioId: 1, name:name}]")
        List<StudioDto> studios,
        @Schema(description = "List with the themes",
                example = "[{themeId: 1, name:name}]")
        List<ThemeDto> themes
) {
}
