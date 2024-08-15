package entertain_me.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entertain_me.app.vo.ProblemVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CustomAuthenticationEntryPointConfig implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        var authenticationError = ProblemVo.builder().message(customErrorMessageAuthentication(authException)).build();

        Gson gson = new GsonBuilder().create();

        response.getWriter().write(String.valueOf(gson.toJson(authenticationError)));
        response.getWriter().flush();
    }

    private String customErrorMessageAuthentication(AuthenticationException exception) {
        log.error("Authentication error of type -> {}", exception.getMessage());
        String handledMessage;

        if (exception instanceof InternalAuthenticationServiceException) {
            handledMessage = "Email or Password is incorrect. Please, try again.";
        } else if (exception instanceof InsufficientAuthenticationException) {
            handledMessage = "Token is null or invalid.";
        } else {
            handledMessage = "Internal error, contact the support.";
        }

        return handledMessage;
    }
}
