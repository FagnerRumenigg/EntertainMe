package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.OffsetDateTime;

@Builder
@Schema (name = "ProblemVo")
public record ProblemVo(
        @Schema(name = "message")
        String message
){ }