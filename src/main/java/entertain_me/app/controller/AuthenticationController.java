package entertain_me.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entertain_me.app.config.TokenService;
import entertain_me.app.model.User;
import entertain_me.app.record.user.AuthenticationRecord;
import entertain_me.app.record.user.LoginResponseDto;
import entertain_me.app.record.user.RegisterRecord;
import entertain_me.app.service.AuthorizationService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private AuthorizationService service;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRecord dto) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
		var auth = this.authenticationManager.authenticate(userNamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDto(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRecord dto){
		if(this.service.findByLogin(dto.email()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		User newUser = new User(dto.nome(), dto.email(), encryptedPassword, dto.role());

		service.save(newUser);

		return ResponseEntity.ok().build();
	}
}
