package entertain_me.app.dto.user;

import entertain_me.app.enums.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Register")
public record RegisterDto(
		@Schema(name = "name")
		@NotNull
		String name,
		@Schema(name = "email")
		@NotNull
		String email,
		@Schema(name = "password")
		@NotNull
		String password,
		@Schema(name = "role", defaultValue = "USER")
		@NotNull
		UserRoleEnum role) {
}
