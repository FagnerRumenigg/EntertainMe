package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginReturn")
public record LoginResponseVo(
        @Schema(name = "token")
        String token) {

}
