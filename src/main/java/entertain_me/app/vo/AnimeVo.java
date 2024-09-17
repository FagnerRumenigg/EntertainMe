package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "AnimeReturn")
public record AnimeVo(
    Long id,
    @Schema(name = "title")
    String title,
    @Schema(name = "source")
    String source,
    @Schema(name = "status")
    String status,
    String ageRating,
    @Schema(name = "synopsys")
    String synopsys,
    @Schema(name = "episodes")
    Integer episodes,
    @Schema(name = "year")
    Integer year){
}