package plainkanban.backend.user;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(new AntPathRequestMatcher("/api/v1/log-in"));
    this.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    boolean isPostMethod = request.getMethod().equals("POST");
    if (!isPostMethod) {
      throw new IllegalStateException("Only post method is supported for login");
    }

    LogInDto logInDto = new ObjectMapper().readValue(request.getReader(), LogInDto.class);

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(logInDto.getEmail(),
        logInDto.getPassword());

    Authentication authenticate = getAuthenticationManager().authenticate(token);

    return authenticate;
  }

}
