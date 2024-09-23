package entertain_me.app.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(name = "User Interaction",
        description = "Dto used to save the user interaction with the anime")
public record UserInteractionDto(

    @Schema(
        description = "Anime ID",
        example = "1"
    )
    @NotNull
    Long idAnime,

    @Schema(
            description = "Anime rating",
            example = "1, 2 or 3"
    )
    @NotNull
    @Min(1)
    @Max(3)
    Short rating,

    @Schema(
            description = "If 'noInterest' is true, the user will not see that anime anymore",
            example = "true or false"
    )
    Boolean noInterest,

    @Schema(
            description = "If 'myList' is true, the user will see the anime in myList list",
            example = "true or false"
    )
    Boolean myList,

    @Schema(
            description = "If 'isWatching' is true, the user will see the anime in is watching list",
            example = "true or false"
    )
    Boolean isWatching,

    @Schema(
            description = "If 'isWatched' is true, the user will see the anime in is watched list",
            example = "true or false"
    )
    Boolean isWatched) {
}
