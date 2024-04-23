package entertain_me.app.vo;

import java.util.List;

public record AnimeVO(
    String title,
    String source,
    String status,
    String synopsys,
    Integer episodes,
    Integer year,
    List<String> demographics,
    List<String> studios,
    List<String> genres) {
}