package com.example.Weather.WeatherApplication.transformer;

import com.example.Weather.WeatherApplication.domain.CityWeather;
import com.example.Weather.WeatherApplication.entity.OpenWeatherResponseEntity;
import com.example.Weather.WeatherApplication.entity.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class OpenWeatherTransformer {

    public CityWeather transformToDomain(OpenWeatherResponseEntity entity){

        return CityWeather.builder()
                .weather(entity.getWeather()[0].getMain())
                .details(entity.getWeather()[0].getDescription())
                .build();
    }
    public WeatherResponse transformToEntity(final CityWeather cityWeather){

        return WeatherResponse.builder()
                .weather(cityWeather.getWeather())
                .details(cityWeather.getDetails())
                .build();
    }
}
