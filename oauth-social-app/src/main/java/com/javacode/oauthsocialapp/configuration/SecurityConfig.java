package com.javacode.oauthsocialapp.configuration;
import com.javacode.oauthsocialapp.service.CustomOAuth2UserService;
import com.javacode.oauthsocialapp.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomOAuth2UserService oAuth2UserService) {
        this.userDetailsService = customUserDetailsService;
        this.oAuth2UserService = oAuth2UserService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/", "/login**", "/error").permitAll()
                .requestMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated());

        http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService);

        http.oauth2Login(oauth2Login -> {
            if (this.oAuth2UserService != null) {
                oauth2Login.userInfoEndpoint(infoEndPoint -> infoEndPoint.userService(this.oAuth2UserService));
            }
            if (this.successHandler != null) {
                oauth2Login.successHandler(this.successHandler);
            }
            if (this.failureHandler != null) {
                oauth2Login.failureHandler(this.failureHandler);
            }
        });

        return http.formLogin(Customizer.withDefaults()).build();
    }

}
