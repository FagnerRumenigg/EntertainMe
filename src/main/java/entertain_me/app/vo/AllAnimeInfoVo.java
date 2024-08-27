package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name= "AllAnimeInfo")
public record AllAnimeInfoVo(
        @Schema(name = "title")
        String title,
        @Schema(name = "source")
        String source,
        @Schema(name = "status")
        String status,
        @Schema(name = "ageRating")
        String ageRating,
        @Schema(name = "synopsys")
        String synopsys,
        @Schema(name = "episodes")
        Integer episodes,
        @Schema(name = "year")
        Integer year,
        @Schema(name = "demographics")
        List<String> demographics,
        @Schema(name = "studios")
        List<String> studios,
        @Schema(name = "genres")
        List<String> genres) {
}
