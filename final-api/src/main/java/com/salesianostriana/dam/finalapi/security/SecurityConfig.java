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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/auth/register").anonymous()
                .antMatchers("/auth/register/admin").anonymous()
                .antMatchers("/auth/login").anonymous()
                .antMatchers("/usernameavailable/**").anonymous()
                .antMatchers("/site/").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/site/public").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"follow/list").anonymous() //TODO: Change to USER or ADMIN
                .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http.headers().frameOptions().disable();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
