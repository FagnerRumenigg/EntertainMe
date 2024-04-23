package entertain_me.app.record.user;

import entertain_me.app.enums.UserRoleEnum;

public record RegisterRecord(
		String nome,
		String email,
		String password,
		UserRoleEnum role) {}
