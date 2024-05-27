package com.example.Weather.WeatherApplication.provider;

import com.example.Weather.WeatherApplication.domain.CityCoordinates;
import com.example.Weather.WeatherApplication.entity.Response10;
import com.example.Weather.WeatherApplication.entity.Response10Repository;
import com.example.Weather.WeatherApplication.entity.TempEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class Res10Provider {

    @Autowired
    Response10Repository response10Repository;

    @Value("${daily.url}")
    private String dailyUrl;

    @Value("${api.key}")
    private String apiKey;

    public Response10 getWeatherNext10(final CityCoordinates cityCoordinates) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;


        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(dailyUrl)
                .queryParam("lat", cityCoordinates.getLatitude())
                .queryParam("lon", cityCoordinates.getLongitude())
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .build();
        System.out.println(uriBuilder.toUriString());
        try {
            responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET, requestEntity,
                    String.class);

            String jsonResponse = responseEntity.getBody();
            return mapJsonToTempEntity(jsonResponse,cityCoordinates);
        } catch (HttpStatusCodeException e) {
            throw new Exception(e.getMessage());
        }


    }

    private Response10 mapJsonToTempEntity(String jsonResponse,CityCoordinates cityCoordinates) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);

        JsonNode listNode = rootNode.path("list");
        List<TempEntity> tempEntities = new ArrayList<>();

        Response10 response10 = Response10.builder()
                .latitude(cityCoordinates.getLatitude().toString())
                .longitude(cityCoordinates.getLongitude().toString())
                .tempEntities(tempEntities)
                .build();

        if (listNode.isArray()) {
            for (JsonNode node : listNode) {
                String date = node.path("dt_txt").asText();
                String weatherDescription = node.path("weather").get(0).path("description").asText();
                String temp = node.path("main").path("temp").asText();
                String feels_like = node.path("main").path("feels_like").asText();
                String temp_min = node.path("main").path("temp_min").asText();
                String temp_max = node.path("main").path("temp_max").asText();
                String humidity = node.path("main").path("humidity").asText();

                TempEntity tempEntity = TempEntity.builder()
                        .date(date)
                        .weatherDescription(weatherDescription)
                        .temp(temp)
                        .feels_like(feels_like)
                        .temp_min(temp_min)
                        .temp_max(temp_max)
                        .humidity(humidity)
                        .response10(response10)
                        .build();

                tempEntities.add(tempEntity);
            }
        }
        // Save the Response10 instance, which will cascade and save the TempEntity instances
        response10Repository.save(response10);

        return response10;
    }

}
