package entertain_me.app.dto.user;

import entertain_me.app.enums.UserRoleEnum;
import jakarta.validation.constraints.NotNull;

public record RegisterRecord(
		@NotNull
		String name,
		@NotNull
		String email,
		@NotNull
		String password,
		@NotNull
		UserRoleEnum role) {
}
