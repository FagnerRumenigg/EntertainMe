package entertain_me.app.dto.recommendation;

import java.util.List;

public record PreferencesDto(
        List<Long> genres,
        List<Long> studios,
        List<Long> demographics,
        List<Long> themes
) {
}
