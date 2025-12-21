package com.bookings.vcbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UiSecurityConfig{
	
    @Autowired
	 private UserDetailsService userDetailsService;

    @Autowired
    private LogoutSuccessHandler customlogOutSuccessHandler;
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/fontawesome/**", "/fontfamily/**").permitAll()
                    .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
				                    .defaultSuccessUrl("/dashboard-page", true)
				                    .failureUrl("/login?error")
				                    .usernameParameter("username")
				                    .passwordParameter("password"))
                
                .logout(logout -> logout.logoutSuccessHandler(customlogOutSuccessHandler)
                						.addLogoutHandler(logoutSuccessHandler()))
                
                .exceptionHandling(ex -> ex.accessDeniedPage("/login"))
                .csrf(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions().sameOrigin())
                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						                	    .invalidSessionUrl("/sessionExpired")
						                	    .sessionConcurrency(concurrency -> concurrency
						            	        .maximumSessions(5)
						            	        .maxSessionsPreventsLogin(false)
						                	    ));

        return http.build();
    }
	
	@Bean
	AuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	PasswordEncoder passwordEncoder() throws Exception{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	LogoutHandler logoutSuccessHandler() {
		return new CustomLogoutHandler();
	}
}
