package entertain_me.app.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertain_me.app.vo.ProblemVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import entertain_me.app.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class SecurityFilterConfig extends OncePerRequestFilter {

	@Autowired
	private TokenServiceConfig tokenService;

	@Autowired
	private AuthenticationService authorizationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			var token = tokenService.recoverToken(request);

			if (token != null) {
				String jti = tokenService.getJtiFromToken(token);
				if (jti != null && tokenService.isBlacklisted(jti)) {
					String message = "Token is blacklisted";
					log.error(message);

					handleReturnException(message, response, HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
				var login = tokenService.validateToken(token);
				if (!login.isEmpty()) {
					UserDetails user = authorizationService.findByLogin(login);
					if (user != null) {
						var authentication = new UsernamePasswordAuthenticationToken(
								user, null, user.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			filterChain.doFilter(request, response);
		} catch(Exception e) {
			String message = "Something is wrong, please, try again in a few minute";
			log.error("{} - {} : {}", message, e.getClass(), e.getMessage());

			handleReturnException(message, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void handleReturnException(String message, HttpServletResponse response, Integer responseStatus) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ProblemVo errorResponse = new ProblemVo("Token is blacklisted");

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		response.getWriter().flush();
	}
}