package com.example.Weather.WeatherApplication.provider;

import com.example.Weather.WeatherApplication.domain.WeatherRequestDetails;
import com.example.Weather.WeatherApplication.entity.GeocodingCoordinatesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class GeocodingProvider {

    @Value("${api.key}")
    private String apiKey;
    @Value("${geocoding.url}")
    private String geoCodingUrl;

    public GeocodingCoordinatesEntity getCoordinates(final WeatherRequestDetails weatherRequestDetails) throws Exception {

        //Geocoding API Call

        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<GeocodingCoordinatesEntity> responseEntity;

        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);
        /*
        * Build URL
        * */
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(geoCodingUrl)
                .queryParam("zip", weatherRequestDetails.getZipcode())
                .queryParam("appid", apiKey).build();

        System.out.println(uriBuilder.toUriString());
        try {
            responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET, requestEntity,
                    GeocodingCoordinatesEntity.class);
        } catch (HttpStatusCodeException e) {
            throw new Exception(e.getMessage());
        }
        responseEntity.getBody().setPincode(weatherRequestDetails.getZipcode());
        //save
        log.info("Calling API");
        return responseEntity.getBody();


    }
}
