package entertain_me.app.controller;

import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.vo.ProblemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.config.TokenServiceConfig;
import entertain_me.app.model.User;
import entertain_me.app.dto.user.AuthenticationDto;
import entertain_me.app.vo.LoginResponseVo;
import entertain_me.app.dto.user.RegisterDto;
import entertain_me.app.service.AuthenticationService;
import jakarta.validation.Valid;

@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication")
@CrossOrigin
@Log4j2
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private AuthenticationService authenticationService;

	@Autowired
	private TokenServiceConfig tokenService;

	@Operation(summary = "Does the user login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User authenticated successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseVo.class))),
			@ApiResponse(responseCode = "404", description = "The user's email was not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))),
			@ApiResponse(responseCode = "401", description = "The user's password is incorrect",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))),
			@ApiResponse(responseCode = "500", description = "Internal error",
					content = { @Content(mediaType  = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto userAuthentication) throws BadCredentialsException, Exception {
		var userNamePassword = new UsernamePasswordAuthenticationToken(userAuthentication.email(), userAuthentication.password());
			Authentication auth = authenticationManager.authenticate(userNamePassword);
			User user = (User) auth.getPrincipal();
			String token = tokenService.generateToken(user);

			log.info("User: "+ user.getName() +" logged");
			return ResponseEntity.ok(new LoginResponseVo(token, "Bearer", 3600,user.getId(), user.getName(), user.getEmail()));
	}

	@Operation(summary = "Does the user register", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User registered successfully"),
			@ApiResponse(responseCode = "403", description = "The user's email is not in the correct format",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
			@ApiResponse(responseCode = "403", description = "The user's email is already registered",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
			@ApiResponse(responseCode = "403", description = "The user's password it is not in the pattern",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
			@ApiResponse(responseCode = "500", description = "Internal error",
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerUser) throws AlreadyExistsException, EmailNotValidException, IncorrectPasswordException {
		authenticationService.save(registerUser);

		log.info("User created");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Does the user logout", method = "POST", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User logout"),
			@ApiResponse(responseCode = "404", description = "The user's email was not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))),
			@ApiResponse(responseCode = "401", description = "The user's password is incorrect",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))),
			@ApiResponse(responseCode = "500", description = "Internal error",
					content = { @Content(mediaType  = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		authenticationService.logout(tokenService.recoverToken(request));
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
