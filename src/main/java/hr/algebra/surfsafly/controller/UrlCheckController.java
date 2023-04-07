package hr.algebra.surfsafly.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.safebrowsing.v4.Safebrowsing;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesRequest;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesResponse;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4ThreatInfo;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("api")
public class UrlCheckController {

    @Value("${google.safebrowsing.api.key}")
    private String googleSafeBrowsingApiKey;

    public static final GsonFactory GOOGLE_JSON_FACTORY = GsonFactory.getDefaultInstance();
    public static final String APPLICATION_NAME = "SurfSafely";
    public static NetHttpTransport httpTransport;


    @PostMapping("/checkUrl")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto> checkUrl(@RequestBody GoogleSecuritySafebrowsingV4ThreatInfo json) throws GeneralSecurityException, IOException {

        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Safebrowsing safebrowsing = new Safebrowsing.Builder(
                httpTransport,
                GOOGLE_JSON_FACTORY,
                null
        ).setApplicationName(APPLICATION_NAME).build();

        var request = new GoogleSecuritySafebrowsingV4FindThreatMatchesRequest();
        request.setThreatInfo(json);
        request.setThreatInfo(json);

        Safebrowsing.ThreatMatches.Find finder = safebrowsing.threatMatches().find(request).setKey(googleSafeBrowsingApiKey);

        GoogleSecuritySafebrowsingV4FindThreatMatchesResponse results = finder.execute();

        return new ResponseEntity<>(ApiResponseDto.ok(results), HttpStatus.OK);
    }
}
