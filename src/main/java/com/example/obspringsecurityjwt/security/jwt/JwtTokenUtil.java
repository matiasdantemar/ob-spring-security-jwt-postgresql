package com.example.obspringsecurityjwt.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// se encarga de construir el token

/**
 * Métodos para generar y validar los token JWT
 */
@Component
public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;


    // genera el token, cuando el usuario se esta logeando
    public String generateJwtToken(Authentication authentication) { //aqui se genera el token

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder() //Jwts estamos utilizando una clase de la dependencia que añadimos en maven para generar el token
                .setSubject((userPrincipal.getUsername())) // en los filtros se puede extraer el username y cargarlo de la BD
                .setIssuedAt(new Date()) //se añade una fecha
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // se añade una expiracion
                .signWith(getSecret(jwtSecret)) // se firma con un algoritmo y un secret
                .compact();
    }


    // obtener el userName a partir del token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecret(jwtSecret)).build().parseClaimsJws(token).getBody().getSubject();
    }


    // valida el token, verifica que no sea invalido, que la signatura este bien, que no este mal formado, no este expirado
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(jwtSecret)).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private Key getSecret(String secret){
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}
