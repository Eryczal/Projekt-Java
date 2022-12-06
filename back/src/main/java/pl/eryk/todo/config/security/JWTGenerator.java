package pl.eryk.todo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24;

    @Value("${jwt.secret:supersecretjsonwebtoken}")
    private String secret;

    private Algorithm algorithm;

    public Algorithm getAlgorithm() {
        if(algorithm == null) {
            algorithm = Algorithm.HMAC512(secret);
        }

        return algorithm;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);

        String token = JWT.create()
                .withSubject(username)
                .withIssuer("todoApp")
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .sign(getAlgorithm());

        return token;
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        String subject = decodedJWT.getSubject();

        return subject;
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).withIssuer("todoApp").build();

            verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT Token!");
        }

        return true;
    }
}
