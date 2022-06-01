package com.salesianostriana.dam.finalapi.security;

import com.salesianostriana.dam.finalapi.security.jwt.JwtAuthenticationEntryPoint;
import com.salesianostriana.dam.finalapi.security.jwt.JwtAuthorizationFilter;
import com.salesianostriana.dam.finalapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final JwtAuthorizationFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                .antMatchers(HttpMethod.POST, "/auth/register").anonymous()
                .antMatchers(HttpMethod.POST, "/auth/register/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/register/propietario").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{username}/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/site/").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.PUT, "/site/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "/site/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.GET, "/propietario/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .antMatchers(HttpMethod.DELETE, "/propietario/{id}").hasAnyRole("ADMIN", "PROPIETARIO")
                .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // Para dar acceso a h2
        http.headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}