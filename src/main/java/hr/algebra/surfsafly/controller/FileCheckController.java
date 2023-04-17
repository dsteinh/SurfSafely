package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.ScanResultDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api")
public class FileCheckController {

    @Value("${virustotal.api.key}")
    private String virustotalApiKey;

    private final String VIRUSTOTAL_API_URL = "https://www.virustotal.com/api/v3/files/{sha256}";

    @PostMapping("/checkFile")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponseDto> checkFile(@RequestBody MultipartFile file) throws IOException {

        byte[] fileContent = file.getBytes();
        String sha256 = DigestUtils.sha256Hex(fileContent);
        String url = VIRUSTOTAL_API_URL.replace("{sha256}", sha256);


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-apikey", virustotalApiKey)
                .build();

        Response response = client.newCall(request).execute();

        return new ResponseEntity<>(ApiResponseDto.ok(response), HttpStatus.OK);
    }
}