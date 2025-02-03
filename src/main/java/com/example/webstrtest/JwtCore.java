package com.example.webstrtest;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtCore {

    @Value("${app.secret}")
    private String secret;
    @Value("${app.lifetime}")
    private int lifetime;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().subject(userDetails.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getNameFromJwt(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
