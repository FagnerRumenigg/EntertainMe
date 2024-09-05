package entertain_me.app.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Schema(name = "PreferencesRecommendationDto",
        description = "DTO used to save the user preferences about the recommendations")
public record PreferencesDto(
        @Schema(
                description = "List of genre IDs that the user prefers",
                example = "[1, 2, 3]"
        )
        List<Long> genres,
        @Schema(
                description = "List of studios IDs that the user prefers",
                example = "[1, 2, 3]"
        )
        List<Long> studios,
        @Schema(
                description = "List of demographics IDs that the user prefers",
                example = "[1, 2, 3]"
        )
        List<Long> demographics,
        @Schema(
                description = "List of themes IDs that the user prefers",
                example = "[1, 2, 3]"
        )
        List<Long> themes
) {
}
