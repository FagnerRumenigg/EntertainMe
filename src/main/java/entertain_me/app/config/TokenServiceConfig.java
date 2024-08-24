package entertain_me.app.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import entertain_me.app.model.User;


@Log4j2
@Service
public class TokenServiceConfig {

	@Value("${api.security.token.secret}")
	private String secret;

	@Autowired
	RedisTemplate<String, String> redisTemplate;


	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.create()
					.withIssuer("entertainMe-api")
					.withSubject(user.getEmail())
					.withJWTId(UUID.randomUUID().toString())
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
		}catch(JWTCreationException jwtCreationException) {
			throw new RuntimeException("Error while generating token", jwtCreationException);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm)
					.withIssuer("entertainMe-api")
					.build()
					.verify(token)
					.getSubject();
			
		}catch(JWTVerificationException jwtVerificationException) {
			return "";
		}
	}
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.of("-03:00"));
	}
	public void addToBlacklist(String jti, long expirationTimeInSeconds) {
		redisTemplate.opsForValue().set(jti, "", expirationTimeInSeconds, TimeUnit.SECONDS);
	}
	public boolean isBlacklisted(String jti) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(jti));
	}
	public String getJtiFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getId(); // Obtém o JTI do token
		} catch (Exception e) {
			// Tratar exceções relacionadas ao decoding do token
			return null;
		}
	}
	public long getExpirationFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expirationDate = decodedJWT.getExpiresAt();
        if (expirationDate != null) {
            long currentTimeMillis = System.currentTimeMillis();
            long expirationTimeMillis = expirationDate.getTime();
            return (expirationTimeMillis - currentTimeMillis) / 1000;
        }
        return 0;
    }
	public String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}
}