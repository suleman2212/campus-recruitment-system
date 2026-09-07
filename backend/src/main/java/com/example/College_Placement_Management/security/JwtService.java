package com.example.College_Placement_Management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService
{
    // Must be a valid Base64 string that decodes to >= 64 bytes (HS512).
    // The previous value was NOT valid Base64 (length not a multiple of 4),
    // so Decoders.BASE64.decode() threw at runtime on every single login/token
    // operation. This is a freshly generated, valid Base64-encoded 64-byte key.
    String secretKey="erJErJE438e475lOKcfjllCChN9Z2UDtaPJfzQWCXWqxfd2OInbGVxfxBBNZkNzwYX0NjLY3ZNpxXHIsLrED8g==";

    public String getToken(String username)
    {
        return getToken(username, new HashMap<>());
    }

    public String getToken(String username, Map<String,Object> extraClaims)
    {
        return Jwts.builder()
                .claims()
                .add(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .and()
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey()
    {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    //it is used to verify signature and we are extracted all the details in jwt tokens
    public Claims verifySignatureAndExtractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token)
    {
        return verifySignatureAndExtractAllClaims(token).getSubject();
    }
    public Date getExpiration(String token)
    {
        return verifySignatureAndExtractAllClaims(token).getExpiration();
    }
    public Boolean isTokenExpire(String token)
    {
        return verifySignatureAndExtractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * True only when the token's signature is valid AND it is not expired.
     * jjwt throws (ExpiredJwtException, SignatureException, MalformedJwtException, ...)
     * instead of returning false, so every failure mode is caught here and
     * treated as "not valid" rather than bubbling up as an unhandled 500.
     */
    public boolean isTokenValid(String token)
    {
        try {
            return !verifySignatureAndExtractAllClaims(token).getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
