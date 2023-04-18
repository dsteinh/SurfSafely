package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.model.JwtBlacklistData;
import hr.algebra.surfsafly.service.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final JwtBlacklistService service;

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(@RequestHeader(name = "Authorization") String token) {
        JwtBlacklistData jwtBlacklistData = JwtBlacklistData.builder()
                .token(token)
                .isExpired(false).build();
        service.save(jwtBlacklistData);
        return ResponseEntity.ok(ApiResponseDto.ok("log out was successful"));
    }
}
