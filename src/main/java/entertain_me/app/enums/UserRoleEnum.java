package entertain_me.app.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

	ADMIN("admin"),
	USER("user");
	
	private final String role;
	
	UserRoleEnum(String role){
		this.role = role;
	}

}
