package com.salesianostriana.dam.finalapi.security.jwt;

import com.salesianostriana.dam.finalapi.models.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Log
@Service
public class JwtProvider {
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret:elsecretodelaapiestaenlamasamaslarga}")
    private String jwtSecret;

    @Value("${jwt.duration:86400}")
    private int jwtLifeInSeconds;

    private JwtParser parser;

    //Esto sirve para inicializar el parser, de lo contrario quedaría como null
    @PostConstruct
    public void init() {
        parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();
    }

    //Método para construir el token jwt
    public String generateToken(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        Date tokenExpirationDate = Date
                .from(LocalDateTime
                        .now()
                        .plusSeconds(jwtLifeInSeconds)
                        .atZone(ZoneId.systemDefault()).toInstant());


        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(userEntity.getId().toString())
                .setIssuedAt(tokenExpirationDate)
                .claim("username", userEntity.getUsername())
                .claim("avatar", userEntity.getAvatar())
                .claim("role", userEntity.getRol().name())
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();


    }

    //Método para conseguir el id de usuario de un token jwt
    public UUID getUserIdFromJwt(String token) {

        return UUID.fromString(parser.parseClaimsJws(token).getBody().getSubject());
    }

    //Método para implementar las excepciones de jsonWebToken
    public boolean validateToken(String token) {

        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            log.info("Token error: " + ex.getMessage());
        }
        return false;

    }

}
