package entertain_me.app.controller;

import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

import entertain_me.app.vo.exception.ErrorsValidateVo;
import entertain_me.app.vo.exception.ProblemVo;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        var errors = ex.getFieldErrors();
        log.error("[ApiExceptionHandler] - MethodArgumentNotValid -> {}", errors);
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorsValidateVo::new).toList());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = ProblemVo.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - HttpMessageNotReadable -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(AlreadyExistsException ex) {
        var error = ProblemVo.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - forbidden -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        var error = ProblemVo.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - notFound -> {}", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<?> handleEmailNotValidException(EmailNotValidException ex) {
        var error = ProblemVo.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - forbidden -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordExceptionn(IncorrectPasswordException ex) {
        var error = ProblemVo.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - forbidden -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}