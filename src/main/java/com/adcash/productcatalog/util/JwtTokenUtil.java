package com.adcash.productcatalog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class performs the creation and validation of jwt tokens.
 */
@Component
public class JwtTokenUtil implements Serializable {

    public static final long serialVersionUUID= -2550185264626066388L;
    @Value("${jwt.secret}")
    private String jwtKey;

    public Integer getCustomerIdFromKey(String token){
        return Integer.parseInt(getClaimFromToken(token, Claims::getSubject));
    }

    public Date getExpiredDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token) {
        Date expiredDate= getExpiredDateFromToken(token);
        return !expiredDate.before(new Date());
    }

    public String generateToken(int userId) {
        return Constants.TOKEN_PREFIX + Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(verifyToken(token)).getBody();
        return claimsResolver.apply(claims);
    }

    private String verifyToken(String token){
        if(token.contains("Bearer ")){
            return token.split(" ")[1];
        }
        return token;
    }
}
