package entertain_me.app.controller;

import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import entertain_me.app.vo.ErrorsValidateVo;
import entertain_me.app.vo.ProblemVo;

import java.nio.file.AccessDeniedException;

@Log4j2
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
        var error = ProblemVo.builder().message(ex.getMessage()).build();
        log.error("[ApiExceptionHandler] - HttpMessageNotReadable -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(AlreadyExistsException ex) {
        var error = ProblemVo.builder().message(ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - AlreadyExistsException -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.info("SOCORRO");
        var error = ProblemVo.builder().message(ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - UsernameNotFoundException -> {}", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<?> handleEmailNotValidException(EmailNotValidException ex) {
        var error = ProblemVo.builder().message(ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - EmailNotValidException -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        var error = ProblemVo.builder().message(ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - IncorrectPasswordException -> {}", error);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        var error = ProblemVo.builder().message(ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - IllegalArgumentException -> {}", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        String errorMessage = "Email or Password invalid";
        var error = ProblemVo.builder().message(errorMessage).build();
        log.error("[ApiExceptionHandler] - BadCredentialsException -> {}", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        String errorMessage = "Email or Password invalid";

        var error = ProblemVo.builder().message(errorMessage).build();
        log.error("[ApiExceptionHandler] - InternalAuthenticationServiceException -> {}", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error(ex);
        var error = ProblemVo.builder().message( ex.getLocalizedMessage()).build();
        log.error("[ApiExceptionHandler] - Exception -> {}", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( ex.getLocalizedMessage());
    }
}