package entertain_me.app.config;

import java.io.IOException;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
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
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write("Token is blacklisted");
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
		} catch (Exception e) {
			String messageError = "Something is wrong, please, try again in a few minutes";
			log.error("Unexpected error: {} : {}", e.getClass(), e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(messageError);
			response.getWriter().flush();
		}
	}
}