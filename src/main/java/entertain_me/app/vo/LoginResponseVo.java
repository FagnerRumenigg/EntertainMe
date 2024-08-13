package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

@Schema(name = "LoginReturn")
public record LoginResponseVo(
        @Schema(name = "token")
        String token,

        @Schema(name = "type")
        String type,

        @Schema(name = "expiresIn")
        Integer expiresIn,

        @Schema(name ="name")
        String name,

        @Schema(name = "email")
        String email){
}
