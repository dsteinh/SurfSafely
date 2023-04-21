package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.model.JwtBlacklistData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface JwtBlacklistService {
    void save(JwtBlacklistData jwtBlacklistData);

    boolean isBlacklisted(String token);

    @Transactional
    void deleteByToken(String token);
}
