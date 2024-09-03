package entertain_me.app.dto.recommendation;

import java.util.List;

public record PreferencesDto(
        List<String> genres,
        List<String> studios,
        List<String> demographics,
        List<String> themes
) {
}
