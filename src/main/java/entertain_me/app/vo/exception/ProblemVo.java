package entertain_me.app.vo.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.OffsetDateTime;

@Builder
@Schema (name = "ProblemReturn")
public record ProblemVo(
        @Schema(name = "message")
        String message
){ }