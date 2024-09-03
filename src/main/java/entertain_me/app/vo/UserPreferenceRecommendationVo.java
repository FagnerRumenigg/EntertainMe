package entertain_me.app.vo;

import entertain_me.app.dto.anime.DemographicDto;
import entertain_me.app.dto.anime.GenreDto;
import entertain_me.app.dto.anime.StudioDto;
import entertain_me.app.dto.anime.ThemeDto;

import java.util.List;

public record UserPreferenceRecommendationVo(
        List<DemographicDto> demographics,
        List<GenreDto> genres,
        List<StudioDto> studios,
        List<ThemeDto> themes
) {
}
