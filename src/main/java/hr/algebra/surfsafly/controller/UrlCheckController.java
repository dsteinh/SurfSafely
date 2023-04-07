package hr.algebra.surfsafly.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.safebrowsing.v4.Safebrowsing;
import com.google.api.services.safebrowsing.v4.model.*;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api")
public class UrlCheckController {

    //Change to docker environment variable
    private String googleSafeBrowsingApiKey = "AIzaSyBq9g4Af77lLKZGN-gm3jAZRd65QvpIvZI";

    public static final GsonFactory GOOGLE_JSON_FACTORY = GsonFactory.getDefaultInstance();
    public static final String APPLICATION_NAME = "SurfSafely";
    public static NetHttpTransport httpTransport;


    @PostMapping("/checkUrl")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto> checkUrl(@RequestBody GoogleSecuritySafebrowsingV4FindThreatMatchesRequest json) throws GeneralSecurityException, IOException {

        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Safebrowsing safebrowsing = new Safebrowsing.Builder(
                httpTransport,
                GOOGLE_JSON_FACTORY,
                null
        ).setApplicationName(APPLICATION_NAME).build();

        Safebrowsing.ThreatMatches.Find request = safebrowsing.threatMatches().find(json).setKey(googleSafeBrowsingApiKey);

        GoogleSecuritySafebrowsingV4FindThreatMatchesResponse results = request.execute();

        return new ResponseEntity<>(ApiResponseDto.ok(results), HttpStatus.OK);
    }
}
