package entertain_me.app.record.user;

import entertain_me.app.enums.UserRoleEnum;

public record RegisterRecord(
		String login,
		String password,
		UserRoleEnum role) {}
