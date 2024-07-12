package com.sparta.kanbanboardproject.global.config;

import com.sparta.kanbanboardproject.domain.user.repository.UserRepository;
import com.sparta.kanbanboardproject.global.jwt.JwtService;
import com.sparta.kanbanboardproject.global.security.JwtAuthenticationFilter;
import com.sparta.kanbanboardproject.global.security.JwtAuthorizationFilter;
import com.sparta.kanbanboardproject.global.security.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    public WebSecurityConfig(JwtService jwtService, UserDetailsServiceImpl userDetailsService,
                             AuthenticationConfiguration authenticationConfiguration,
                             UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .requestMatchers("/users/signup", "/users/login", "/auth/refresh-token", "/error").permitAll()
                        .requestMatchers("/users/logout").hasAnyRole("MANAGER", "USER")
                        .requestMatchers("/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/boards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.POST,
                                "/boards/{board_id}/progress/{progress_id}/cards",
                                "/boards/{board_id}/progresses/{progress_id}/cards/{card_id}/comments").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.PUT,
                                "/boards/{board_id}/progresses/{progress_id}/cards/{card_id}/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.DELETE,
                                "/boards/{board_id}/progresses/{progress_id}/cards/{card_id}").hasAnyRole("MANAGER", "USER")
                        .anyRequest().authenticated()
        );

//        http.exceptionHandling(auth -> {
//            auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//        });

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
