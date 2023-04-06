package hr.algebra.surfsafly.security;

import hr.algebra.surfsafly.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface JwtUtils {
    Map<String, String> generateToken(User user);

    String getUserNameFromJwtToken(String token);

    String getRoleFromJwtToken(String token);

    boolean validateJwtToken(String token, UserDetails userDetails);

}
