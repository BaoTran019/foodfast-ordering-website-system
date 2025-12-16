package com.cnpm.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.security.Key;



@Component
public class TokenProvider {
    private static final String SECRET = "this_is_a_super_secret_key_that_is_at_least_64_characters_long_123456";
    
    private final Key JWT_SECRET = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token hết hạn");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token không hỗ trợ");
        } catch (MalformedJwtException e) {
            System.out.println("Token sai định dạng");
        } catch (SignatureException e) {
            System.out.println("Sai chữ ký token");
        } catch (IllegalArgumentException e) {
            System.out.println("Token rỗng");
        }
        return false;
    }
}
