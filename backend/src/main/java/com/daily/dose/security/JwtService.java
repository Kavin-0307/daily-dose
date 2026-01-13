package com.daily.dose.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private  String secretKey ;


    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
//*//

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);

            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            return username.equals(userDetails.getUsername())
                    && userDetails.isEnabled()
                    && expiration.after(new Date());

        } catch (Exception ex) {
            return false;
        }
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
/*
 * JWT SERVICE DESIGN NOTES
 *
 * This service is responsible for JWT creation and validation only.
 * It does NOT handle authentication, authorization, or user lookup.
 *
 * Token characteristics:
 * - Subject: staff email (acts as unique user identifier)
 * - Stateless: no server-side session tracking
 * - Expiration-based validity (24 hours)
 *
 * Validation strategy:
 * - Token username must match authenticated UserDetails
 * - Token must be unexpired
 *
 * Assumptions:
 * - Email is immutable and globally unique
 * - Token revocation is handled implicitly via expiration
 *
 * IMPORTANT:
 * - Role/authority claims are intentionally omitted for now
 * - Secret key management must be externalized before production
 */

