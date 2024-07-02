package entertain_me.app.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public record UserDto(

        @Schema(name = "id")
        Long id,
        @Schema(name = "Name")
        String name,

        @Schema(name = "Email")
        String email,

        @Schema(name = "Password")
        String password
) {


}
