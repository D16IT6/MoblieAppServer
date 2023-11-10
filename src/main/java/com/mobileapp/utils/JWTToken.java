package com.mobileapp.utils;

import com.mobileapp.DTO.UserDTO;
import com.mobileapp.entitys.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

@Component
public class JWTToken {
    private final String KEY_SECRET="wint";
    private final long JWT_EXPIRATION = 604800000L;
    public String generateToken(User user){
        Date now=new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(Long.toString(user.getUserId()))
                .claim("userName",user.getUserName())
                .claim("urlAvata",user.getUrlAvata())
                .claim("fullName",user.getFullName())
                .claim("describe",user.getDescribe())
                .claim("email",user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,KEY_SECRET)
                .compact();
    }
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(KEY_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
