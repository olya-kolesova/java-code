package com.javacode.simple_security.configuration;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public static BeanPostProcessor clearDataSourceUserName() {
//        return new BeanPostProcessor() {
//
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof HikariDataSource) {
//                    ((HikariDataSource) bean).setUsername("");
//                }
//                return bean;
//            }
//
//        };
//    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("Vladislav")
                .password(bCryptPasswordEncoder().encode("12345"))
                .authorities("USER")
                .build();

        UserDetails admin = User.builder()
                .username("Maria")
                .password(bCryptPasswordEncoder().encode("12345"))
                .authorities("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, admin);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(withDefaults())
            .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/actuator/shutdown").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/movie").hasAuthority("ADMIN")
                .requestMatchers("/api/movie/list").hasAnyAuthority("USER", "ADMIN"));

        return http.build();
    }




}
