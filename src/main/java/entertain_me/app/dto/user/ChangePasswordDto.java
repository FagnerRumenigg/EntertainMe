package entertain_me.app.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChangePaswword")
public record ChangePasswordDto(

        @Schema(name = "Email")
        String email,

        @Schema(name = "CurrentPassword")
        String currentPassword,

        @Schema(name = "NewPassword")
        String newPassword) {
}
