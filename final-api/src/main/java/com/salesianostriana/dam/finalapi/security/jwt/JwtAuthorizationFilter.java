package com.salesianostriana.dam.finalapi.security.jwt;


import com.salesianostriana.dam.finalapi.errors.exceptions.UnauthorizeException;
import com.salesianostriana.dam.finalapi.models.User;
import com.salesianostriana.dam.finalapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private String getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        try {
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

                UUID userId = jwtProvider.getUserIdFromJwt(token);

                Optional<User> userOptional = userService.findById(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    user.getRol().name(),
                                    user.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        } catch (Exception ex) {
            throw new UnauthorizeException("Could not set user authentication in security context");
        }
        filterChain.doFilter(request, response);
    }

}