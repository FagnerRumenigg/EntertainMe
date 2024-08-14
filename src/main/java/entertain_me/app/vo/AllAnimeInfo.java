package entertain_me.app.vo;

import entertain_me.app.model.Demographic;
import entertain_me.app.model.Genre;
import entertain_me.app.model.Studio;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Set;

@Schema(name= "AllAnimeInfo")
public record AllAnimeInfo(
        @Schema(name = "title")
        String title,
        @Schema(name = "source")
        String source,
        @Schema(name = "status")
        String status,
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
