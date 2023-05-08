package com.example.util;

import com.example.dto.JwtDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24*10; // 10-day
    private static final int emailTokenLiveTime = 1000 * 120; // 2-minutes
    private static final String secretKey = "dasda143mazgi";

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDto decode(String token) {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            Integer id = (Integer) claims.get("id");
            String role = (String) claims.get("role");
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDto(id, profileRole);
    }



    public static String decodeEmailVerification(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            return (String) claims.get("email");
        } catch (JwtException e) {
            e.printStackTrace();
        }
        throw new MethodNotAllowedException("Jwt exception");
    }
    public static String encode(String text) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("email", text);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }
    public static JwtDto getJwtDTO(String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        return JwtUtil.decode(jwt);
    }
    public static boolean checkToOwner(String authorization,Integer userId) {
        if (getJwtDTO(authorization).getId()!=userId){
            throw new MethodNotAllowedException("Method not allowed");
        }
        return true;
    }
    public static void checkToAdminOrOwner(String authorization) {
        JwtDto jwtDTO = getJwtDTO(authorization);
        if (!(jwtDTO.getRole().equals(ProfileRole.ADMIN)||checkToOwner(authorization, jwtDTO.getId()))){
            throw new MethodNotAllowedException("Method not allowed");
        }
    }

    public static JwtDto getJwtDTO(String authorization, ProfileRole... roleList) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtDTO.getRole().equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return jwtDTO;
    }
    public static void checkForRequiredRole(HttpServletRequest request, ProfileRole... roleList) {
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtRole.equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
    }
}
