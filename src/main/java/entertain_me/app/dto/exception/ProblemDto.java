package entertain_me.app.dto.exception;

import lombok.Builder;
import java.time.OffsetDateTime;

@Builder
public record ProblemDto(
        String message,

        OffsetDateTime dateTime
){ }