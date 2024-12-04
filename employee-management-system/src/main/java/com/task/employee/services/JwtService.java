package com.task.employee.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource("classpath:application.properties")
public class JwtService {
    //public static final String  SECRET = "nH0WH8q5bzlW8/I1/vvXJAo4FqjHwqCwWOnUV2Nu95ZcE0yKl2VzxJ0NP5mMAIWGU6jjUArpR3HOKZp06h3+K11eSH6BwiOQGPA8K9QodxYkWndmpUAJ3hRNAl7tTVLDP4GC6znAVayNBze6CPcXS8jdhe47S//9jWXKEZWzUnTbAwb2kssnXW4FuvHGCZ/ugFVmnKnR7YskRwacQ5ysV1K1LpF0ZbLDrrH8ovsulGp9x0MgaOW4EqNP9Ggw7OJMZkfzIuPl1Qu74L57igPzhYAE/61Jp1HD8jB0K49V9eXmaK+7jOKsP+MWacf/ZywHWB7b9q+XuEvHlF44pjTtutHH5IMRKB7B8/u23Ua76GE=";
   public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private long jwtExpirationInMs;
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);

    }
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30 ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
      final String username =  extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
