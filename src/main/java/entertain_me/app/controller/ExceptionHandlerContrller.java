package entertain_me.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.OffsetDateTime;

import entertain_me.app.dto.ErrorsValidateRecord;
import entertain_me.app.dto.ProblemDto;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerContrller extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        var errors = ex.getFieldErrors();
        System.out.println("Meu pau");
        log.error("[ApiExceptionHandler] - MethodArgumentNotValid -> {}", errors);
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorsValidateRecord::new).toList());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - HttpMessageNotReadable -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle500Error(Exception ex) {
        var error = ProblemDto.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - internalServerError -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }
}

