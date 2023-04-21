package hr.algebra.surfsafly.security;

import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.service.JwtBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtilsImpl implements JwtUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${app.jwttoken.message}")
    private String message;

    private final JwtBlacklistService jwtBlacklistService;

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 1 day in milliseconds

    @Override
    public Map<String, String> generateToken(User user) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().getName())
                .setIssuedAt(issuedAt)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(expiration).compact();

        removeFromBlacklist(jwtToken);
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }

    private void removeFromBlacklist(String jwtToken) {
        if (jwtBlacklistService.isBlacklisted(jwtToken)) {
            jwtBlacklistService.deleteByToken(jwtToken);
        }
    }


    public boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUserNameFromJwtToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !jwtBlacklistService.isBlacklisted(token);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    @Transactional
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        boolean isExpired = expiration.before(new Date());
        if (isExpired && jwtBlacklistService.isBlacklisted(token)) {
            jwtBlacklistService.deleteByToken(token);
        }
        return isExpired;
    }

    public String getRoleFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }
}
