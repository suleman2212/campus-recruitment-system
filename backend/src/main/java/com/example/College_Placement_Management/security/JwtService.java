package com.example.College_Placement_Management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService
{
    String secretKey="hgsfjbnsxnvrhiuewrhtvbfwfweghjbgjxnasjfcewtyinxcfsfhdghxbxcgvhjbnmlkesdtfghbjngnbsjnxasjsajaonkfslkns";

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

    public Key getKey()
    {

        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    //it is used to verify signature and we are extracted all the details in jwt tokens
    public Claims verifySignatureAndExtractAllClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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
}
