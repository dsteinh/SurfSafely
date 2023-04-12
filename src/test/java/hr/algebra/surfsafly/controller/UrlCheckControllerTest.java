package hr.algebra.surfsafly.controller;

import com.google.api.services.safebrowsing.v4.Safebrowsing;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesRequest;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4FindThreatMatchesResponse;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4ThreatEntry;
import com.google.api.services.safebrowsing.v4.model.GoogleSecuritySafebrowsingV4ThreatInfo;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlCheckControllerTest {

    private UrlCheckController urlCheckController;

    @Mock
    private Safebrowsing safebrowsing;

    private final String googleSafeBrowsingApiKey = "AIzaSyBq9g4Af77lLKZGN-gm3jAZRd65QvpIvZI";

    @BeforeEach
    void setUp() {
        urlCheckController = new UrlCheckController(safebrowsing, googleSafeBrowsingApiKey);
    }

    @Test
    void checkUrl_shouldReturnApiResponseDtoWithResults_whenCalledWithValidRequest() throws GeneralSecurityException, IOException {

        GoogleSecuritySafebrowsingV4ThreatInfo json = new GoogleSecuritySafebrowsingV4ThreatInfo();
        json.setThreatTypes(Arrays.asList("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE"));
        json.setPlatformTypes(Arrays.asList("ANY_PLATFORM"));
        json.setThreatEntryTypes(Arrays.asList("URL"));
        GoogleSecuritySafebrowsingV4ThreatEntry url1 = new GoogleSecuritySafebrowsingV4ThreatEntry();
        url1.setUrl("http://openai.com/");
        GoogleSecuritySafebrowsingV4ThreatEntry url2 = new GoogleSecuritySafebrowsingV4ThreatEntry();
        url2.setUrl("https://testsafebrowsing.appspot.com/s/malware.html");
        json.setThreatEntries(Arrays.asList(url1, url2));

        GoogleSecuritySafebrowsingV4FindThreatMatchesRequest request = new GoogleSecuritySafebrowsingV4FindThreatMatchesRequest();
        request.setThreatInfo(json);

        ResponseEntity<ApiResponseDto> result = urlCheckController.checkUrl(json);

    }
}
