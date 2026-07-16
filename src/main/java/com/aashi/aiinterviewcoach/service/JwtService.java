package com.aashi.aiinterviewcoach.service;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@Service
public class JwtService{
    private static final String SECRET =
            "mySuperSecretKeyForJwtAuthentication123456789";
            private final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());

     public String generateKey(String email,String role)
    {
        return Jwts.builder()
               .subject(email)
               .claim("role",role)
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() //System.currentTimeMillis()
                                          +1000*60*60))
               .signWith(key)
               .compact();
    }
    public boolean isTokenValid(String token)
    {
        try{
            Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
}
public String extractRole(String token)
    {
        return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("role",String.class);
    }
    public String extractEmail(String token)
    {
        return Jwts.parser()
               .verifyWith(key)
               .build()
               .parseSignedClaims(token)
               .getPayload()
               .getSubject();
    }
}
