package plainkanban.backend.user;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    var customFilter = new CustomAuthenticationFilter(authenticationManager());
    customFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());

    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/api/v1/sign-up", "/api/v1/log-in")
                .permitAll()
                .anyRequest()
                .authenticated())
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        .securityContext((context) -> context.securityContextRepository(securityContextRepository))
        .authenticationProvider(authenticationProvider)
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager() {
    return new ProviderManager(Arrays.asList(authenticationProvider));
  }

}