package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

@Schema(name = "LoginReturn")
public record LoginResponseVo(
        @Schema(name = "token")
        String token,
        @Schema(name ="name")
        String name,

        @Schema(name = "email")
         String email,

        @Schema(name = "password")
         String password){

}
