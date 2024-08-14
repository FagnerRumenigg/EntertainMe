package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChangePaswword")
public record ChangePasswordDto(

        @Schema(name = "email")
        String email,

        @Schema(name = "currentPassword")
        String currentPassword,

        @Schema(name = "newPassword")
        String newPassword) {
}
