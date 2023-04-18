package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.model.JwtBlacklistData;
import org.springframework.stereotype.Service;

@Service
public interface JwtBlacklistService {
    void save(JwtBlacklistData jwtBlacklistData);
}
