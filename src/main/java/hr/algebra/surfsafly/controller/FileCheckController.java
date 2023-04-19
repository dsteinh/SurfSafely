package hr.algebra.surfsafly.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api")
public class FileCheckController {

    @Value("${virustotal.api.key}")
    private String virusTotalApiKey;

    @Value("${virustotal.sha.url}")
    private String virusTotalUrl;

    @PostMapping("/checkFile")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponseDto> checkFile(@RequestBody MultipartFile file) throws IOException {

        byte[] fileContent = file.getBytes();
        String sha256 = DigestUtils.sha256Hex(fileContent);
        String url = virusTotalUrl.replace("{sha256}", sha256);


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-apikey", virusTotalApiKey)
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonResponse = objectMapper.readTree(response.body().string());


        return new ResponseEntity<>(ApiResponseDto.ok(jsonResponse), HttpStatus.OK);
    }
}