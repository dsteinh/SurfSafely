package hr.algebra.surfsafly.configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.safebrowsing.v4.Safebrowsing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class SafebrowsingConfiguration {

    @Bean
    public Safebrowsing safebrowsing() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory gsonFactory = GsonFactory.getDefaultInstance();
        return new Safebrowsing.Builder(httpTransport, gsonFactory, null)
                .setApplicationName("SurfSafely")
                .build();
    }

}
