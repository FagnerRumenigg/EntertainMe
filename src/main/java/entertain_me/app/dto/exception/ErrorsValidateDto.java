package entertain_me.app.dto.exception;

import org.springframework.validation.FieldError;

public record ErrorsValidateDto(
        String field,

        String message
){
    public ErrorsValidateDto(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
