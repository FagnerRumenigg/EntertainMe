package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EmailPassword")
public record ChangeEmailPasswordDto(
        @Schema(name = "Current-Email")
        String currentEmail,
        @Schema(name = "New-Email")
        String newEmail,

        @Schema(name = "Password")
        String password
) {
}
