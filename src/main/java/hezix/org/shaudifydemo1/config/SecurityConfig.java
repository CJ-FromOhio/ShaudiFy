package hezix.org.shaudifydemo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/","song/**").authenticated()
                        .requestMatchers("/error/**").permitAll()
//                        .requestMatchers("/song/**").anonymous()
                        .requestMatchers("/user/**").hasRole("ADMIN"))
                .formLogin(login -> login.defaultSuccessUrl("/"))
                .exceptionHandling(exception -> exception.accessDeniedPage("/error/403"))
                .build();
    }

}
