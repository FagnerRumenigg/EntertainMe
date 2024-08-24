package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

@Schema(name = "ErrorsReturn")
public record ErrorsValidateVo(
        @Schema(name = "field")
        String field,

        @Schema(name = "message")
        String message
){
    public ErrorsValidateVo(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
