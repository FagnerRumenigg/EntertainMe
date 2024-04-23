package entertain_me.app.record.anime;

import java.util.List;

public record AnimeReturn(
        Integer jikanId,
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