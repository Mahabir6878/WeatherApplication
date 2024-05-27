package com.example.Weather.WeatherApplication.transformer;

import com.example.Weather.WeatherApplication.domain.CityCoordinates;
import com.example.Weather.WeatherApplication.entity.GeocodingCoordinatesEntity;
import org.springframework.stereotype.Service;

@Service
public class GeocodingCoordinatedTranformer {

    public CityCoordinates transformToDomain(final GeocodingCoordinatesEntity entity){
        return CityCoordinates.builder()
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
