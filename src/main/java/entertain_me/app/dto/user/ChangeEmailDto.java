package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChangeEmail")
public record ChangeEmailDto(
        @Schema(name = "Current-Email")
        String currentEmail,
        @Schema(name = "New-Email")
        String newEmail,

        @Schema(name = "Password")
        String password
) {
}
