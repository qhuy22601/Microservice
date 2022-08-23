package com.qhuy.authenms.jwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Configuration
@PropertySource("classpath:application.properties")
public class JwlUtil {

    @Value("${spring.security.key}")
    private String secret;



    public String generateToken(String subject){
        return Jwts.builder().setIssuer("").setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes())).compact();
    }

    public Claims getClaim(String token){
        return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    public boolean isValid(String token, String email){
        String getTokenEmail = getClaim(token).getSubject();
        return (email.equals(getTokenEmail) && !isExpiredToken(token));
    }

    public boolean isExpiredToken(String token){
        return getClaim(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public  String getSubject(String token){
        return getClaim(token).getSubject();
    }

}
