package com.example.College_Placement_Management.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class JwtFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHead=request.getHeader("Authorization");
        String token=null;
        if(authHead != null && authHead.startsWith("Bearer"))
        {
            token=authHead.substring(7);
        }

        //this is == null because if we dont have the security context then only we set the security context
        if(token!=null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            Claims claims =jwtService.verifySignatureAndExtractAllClaims(token);
            if(!jwtService.isTokenExpire(token))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                        (claims.getSubject(),null,new ArrayList<>());
                //if we want any aditional deatails we can pass through this
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request,response);


    }
}
