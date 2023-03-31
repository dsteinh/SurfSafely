package hr.algebra.surfsafly.security;

import hr.algebra.surfsafly.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface JwtGenerator {
    Map<String, String> generateToken(User user);
}
