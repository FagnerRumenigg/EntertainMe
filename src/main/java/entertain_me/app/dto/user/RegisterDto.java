package entertain_me.app.dto.user;

import entertain_me.app.enums.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Register")
public record RegisterDto(
		@Schema(name = "name")
		@NotNull
		@Size(max = 100)
		String name,
		@Schema(name = "email")
		@NotNull
		@Size(max = 100)
		String email,
		@Schema(name = "password")
		@NotNull
		@Size(max = 256)
		String password,
		@Schema(name = "role", defaultValue = "USER")
		@NotNull
		UserRoleEnum role) {
}