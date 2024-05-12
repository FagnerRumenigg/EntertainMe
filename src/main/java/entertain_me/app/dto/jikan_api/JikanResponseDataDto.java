package entertain_me.app.dto.jikan_api;

import entertain_me.app.dto.jikan_api.DemographicsDto;
import entertain_me.app.dto.jikan_api.GenreDto;
import entertain_me.app.dto.jikan_api.StudioDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record JikanResponseDataDto(
        @Schema(name = "jikanId")
        Integer mal_id,
        @Schema(name = "title")
        String title,
        @Schema(name = "source")
        String source,
        @Schema(name = "status")
        String status,
        @Schema(name = "synopsys")
        String synopsis,
        @Schema(name = "episodes")
        Integer episodes,
        @Schema(name = "year")
        Integer year,
        @Schema(name = "demographics")
        List<DemographicsDto> demographics,
        @Schema(name = "studios")
        List<StudioDto> studios,
        @Schema(name = "genres")
        List<GenreDto> genres) {
}
