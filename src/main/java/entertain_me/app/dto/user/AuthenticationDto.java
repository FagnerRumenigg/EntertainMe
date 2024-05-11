package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Authentication")
public record AuthenticationDto(

        @Schema(name = "email")
        @NotNull
        String email,
        @Schema(name = "password")
        @NotNull
        String password) {}