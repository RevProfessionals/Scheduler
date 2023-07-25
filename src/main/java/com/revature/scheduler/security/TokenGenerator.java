package com.revature.scheduler.security;

import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenGenerator {
    private final SecretKey secretKey;
    private final UserDAO userDAO;

    @Autowired
    public TokenGenerator(@Value("${jwt.key}") String key, UserDAO userDAO) {
        this.secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        this.userDAO = userDAO;
    }

    public String generateToken(Authentication auth) {
        String email = auth.getName();
        Instant issued = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issued.plus(60, ChronoUnit.MINUTES);
        User user = userDAO.findByEmail(email);

        return Jwts.builder()
            .setSubject(email)
            .claim("id", user.getId())
//            .claim("role", user)
            .claim("first_name", user.getFirstName())
            .claim("last_name", user.getLastName())
            .setIssuedAt(Date.from(issued))
            .setExpiration(Date.from(expiration))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid/Expired token");
        }
    }

    public Claims getClaims(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
