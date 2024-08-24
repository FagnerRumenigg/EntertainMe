package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "AnimeReturn")
public record AnimeVo(
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
    Integer year){
}