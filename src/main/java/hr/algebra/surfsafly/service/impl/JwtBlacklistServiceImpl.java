package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.model.JwtBlacklistData;
import hr.algebra.surfsafly.repository.JwtBlacklistRepository;
import hr.algebra.surfsafly.service.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
    private final JwtBlacklistRepository jwtBlacklistRepository;

    @Override
    public void save(JwtBlacklistData jwtBlacklistData) {
        jwtBlacklistRepository.save(jwtBlacklistData);
    }
}
