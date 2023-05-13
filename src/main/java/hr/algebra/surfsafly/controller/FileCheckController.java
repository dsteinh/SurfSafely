package hr.algebra.surfsafly.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


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
        String encodedFileContent = Base64.getEncoder().encodeToString(fileContent);

        String boundary = "---011000010111000001101001";
        String fileType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String contentDisposition = "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"";

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=" + boundary);
        okhttp3.RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, okhttp3.RequestBody.create(MediaType.parse(fileType), fileContent))
                .build();


        OkHttpClient uploadClient = new OkHttpClient();

        Request uploadRequest = new Request.Builder()
                .url("https://www.virustotal.com/api/v3/files")
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", virusTotalApiKey)
                .addHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .build();

        Response uploadResponse = uploadClient.newCall(uploadRequest).execute();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonUploadResponse = objectMapper.readTree(uploadResponse.body().string());
        String selfLink = jsonUploadResponse.get("data").get("links").get("self").asText();


        String sha256 = DigestUtils.sha256Hex(fileContent);
        String url = virusTotalUrl.replace("{sha256}", sha256);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(selfLink)
                .addHeader("x-apikey", virusTotalApiKey)
                .build();

        Response response = client.newCall(request).execute();

        JsonNode jsonResponse = objectMapper.readTree(response.body().string());


        return new ResponseEntity<>(ApiResponseDto.ok(jsonResponse), HttpStatus.OK);
    }
}