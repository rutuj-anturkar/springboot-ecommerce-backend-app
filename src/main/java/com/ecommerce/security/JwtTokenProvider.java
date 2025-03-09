package com.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // inject values from application.properties

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}") // default: 24 hours
    private int jwtExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        // Option 1: Generate a key from the secret string ensuring it's long enough.
        // Ensure that jwtSecret is at least 64 characters long.
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        // Option 2: Alternatively, generate a new random key:
        // key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // generate jwt token for the given username


    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration((expiryDate))
//                // setting a custom claim , to add granted authorities
//                .claim("authorities", getAuthoritiesInString(userPrincipal.getAuthorities()))
//                // setting a custom claim , to add user id (remove it if not required in the project)
                .claim("user_id", userDetails.getUser().getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // get username from token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder() //create JWT parser
                .setSigningKey(key) //sets the SAME secret key for JWT signature verification
                .build()//rets the JWT parser set with the Key
                .parseClaimsJws(token) //rets JWT with Claims added in the body
                .getBody();//=> JWT valid ,  rets the Claims(payload)


        return claims.getSubject();
    }

    //validate token
    public Claims validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        } catch (JwtException | IllegalArgumentException ex) {
           throw  ex;
        }
    }

    // this method will be invoked by our custom JWT filter to get user id n store it in auth token
    public Long getUserIdFromJwtToken(Claims claims) {
        return Long.valueOf((int) claims.get("user_id"));
    }
}
