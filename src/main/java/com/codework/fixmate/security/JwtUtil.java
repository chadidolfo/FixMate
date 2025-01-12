package com.codework.fixmate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET = "sOiJThTvQYUikEaUYfeo";

    private String createToken(Map<String,Object> claims , String userName){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName) //the token is for
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30)) //30 minutes in milliseconds
                .signWith(SignatureAlgorithm.HS256,getSignKey()).compact();
    }

    private Key getSignKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    public String generateToken (String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    //claims = data stored inside the JWT(username,...)
    private Claims extractAllClaims(String Token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(Token)
                .getPayload();
    }

    public <T> T extractClaim (String Token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(Token);
        return claimResolver.apply(claims);
    }

    public Date extractExpiration (String Token){
        return extractClaim(Token,Claims::getExpiration);
    }

    public String extractUsername(String Token){
        return extractClaim(Token,Claims::getSubject);
    }

    public Boolean isTokenExpired(String Token){
        return extractExpiration(Token).before(new Date());
    }
    public boolean validateToken(String Token, UserDetails userDetails){
        final String userName = extractUsername(Token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(Token));
    }
}
