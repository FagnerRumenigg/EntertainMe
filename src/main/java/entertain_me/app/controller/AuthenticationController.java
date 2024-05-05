package entertain_me.app.controller;

import entertain_me.app.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.config.TokenService;
import entertain_me.app.model.User;
import entertain_me.app.dto.user.AuthenticationDto;
import entertain_me.app.dto.user.LoginResponseDto;
import entertain_me.app.dto.user.RegisterDto;
import entertain_me.app.service.AuthorizationService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private AuthorizationService authorizationService;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto usuarioAuthentication) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(
				usuarioAuthentication.email(), usuarioAuthentication.password());
		var auth = this.authenticationManager.authenticate(userNamePassword);
		var token = tokenService.generateToken((User) (auth).getPrincipal());

		return ResponseEntity.ok(new LoginResponseDto(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerUser) throws AlreadyExistsException {
		authorizationService.save(registerUser);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
