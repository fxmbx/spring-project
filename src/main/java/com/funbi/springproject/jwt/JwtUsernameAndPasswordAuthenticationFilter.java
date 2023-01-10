package com.funbi.springproject.jwt;

import java.time.LocalDate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
     this.authenticationManager = authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

            
                try {
                    UsernameAndPasswordRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordRequest.class);

                    Authentication authencation =  new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

                    return authenticationManager.authenticate(authencation);
                     
                } catch (java.io.IOException e) {
                   throw new RuntimeException(e);
                }
            
    }
    @Override
    protected void successfulAuthentication(
    HttpServletRequest request, 
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult) 
    throws java.io.IOException, ServletException {

        String key= "thisismeanttobeasecuresecretkeybutphwellitiswhatitis";
       String token = Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
            .signWith(Keys.hmacShaKeyFor(key.getBytes()))
            .compact()
            ;


            response.addHeader("Authorization", String.format("Bearer %s", token));
    }


    
}
