package com.daelim.dorandoranbackend.modules;

import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.service.UserJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Autowired
    private UserJwtService userJwtService;

    private String secretKey = "haniumICT!_DoranDoran!@_JWT!@#_secretKey!@#$";
    private final long tokenValidMillisecond = 1000L * 60 * 60;
    private String headerName = "dorandoran-token";


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        String token = Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isAdmin(String token) {
        User user = userJwtService.getUserByUserId(getUserId(token));
        return user.getIsAdmin()!=null;
    }


    public boolean validateToken(HttpServletRequest request) {
        if (request.getHeader(headerName) == null) {
            return false;
        }
        String token = getToken(request);
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
