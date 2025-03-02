package hezix.org.shaudifydemo1.config;

import hezix.org.shaudifydemo1.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/error/**", "/register","/registration", "/css/**", "/actuator/**").permitAll()
                        .requestMatchers("/","song/**").authenticated()
                        .requestMatchers(GET, "user/**").authenticated()
                        .requestMatchers("/user/**", "/api/**").hasRole("ADMIN"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error/403"))
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }
}
