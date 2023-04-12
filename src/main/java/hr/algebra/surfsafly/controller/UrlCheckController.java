package hr.algebra.surfsafly.controller;

import com.google.api.services.safebrowsing.v4.Safebrowsing;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesRequest;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesResponse;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4ThreatInfo;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UrlCheckController {

    private final Safebrowsing safebrowsing;

    @Value("${google.safebrowsing.api.key}")
    private String googleSafeBrowsingApiKey;


    @PostMapping("/checkUrl")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto> checkUrl(@RequestBody GoogleSecuritySafebrowsingV4ThreatInfo json) throws GeneralSecurityException, IOException {
        var request = new GoogleSecuritySafebrowsingV4FindThreatMatchesRequest();
        request.setThreatInfo(json);

        Safebrowsing.ThreatMatches.Find finder = safebrowsing.threatMatches().find(request).setKey(googleSafeBrowsingApiKey);

        GoogleSecuritySafebrowsingV4FindThreatMatchesResponse results = finder.execute();
        return new ResponseEntity<>(ApiResponseDto.ok(results), HttpStatus.OK);
    }

}
