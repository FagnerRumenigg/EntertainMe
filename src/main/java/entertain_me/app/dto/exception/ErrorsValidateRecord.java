package entertain_me.app.dto.exception;

import org.springframework.validation.FieldError;

public record ErrorsValidateRecord(
        String field,

        String message
){
    public ErrorsValidateRecord(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
