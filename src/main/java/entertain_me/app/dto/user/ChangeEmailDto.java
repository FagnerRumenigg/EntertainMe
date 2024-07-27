package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChangeEmail")
public record ChangeEmailDto(
        @Schema(name = "currentEmail")
        String currentEmail,
        @Schema(name = "newEmail")
        String newEmail,

        @Schema(name = "password")
        String password
) {
}
