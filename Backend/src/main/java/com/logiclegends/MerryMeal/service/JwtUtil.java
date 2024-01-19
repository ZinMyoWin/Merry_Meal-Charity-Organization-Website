package com.logiclegends.MerryMeal.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "YourSecretKey"; // This should be externalized
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public String extractEmail(String token) {
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
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println(userDetails.getUsername() + " in JwtUtil");
        
    	Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
    	System.out.println("Hello I'm CreatToken." + claims.isEmpty() + subject);
    	
        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
// Validate Token
  	public boolean validateToken(String authToken) {
  		try {
  			Jwts.parser()
  			.setSigningKey(SECRET_KEY)
  			.parseClaimsJws(authToken);
  			return true;
  		} catch (ExpiredJwtException e) {
  			e.printStackTrace();
  		} catch (UnsupportedJwtException e) {
  			e.printStackTrace();
  		} catch (MalformedJwtException e) {
  			e.printStackTrace();
  		} catch (SignatureException e) {
  			e.printStackTrace();
  		} catch (IllegalArgumentException e) {
  			e.printStackTrace();
  		}
  		return false;
  	}
}
