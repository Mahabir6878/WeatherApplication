package com.example.Weather.WeatherApplication.service;

import com.example.Weather.WeatherApplication.domain.CityCoordinates;
import com.example.Weather.WeatherApplication.domain.CityWeather;
import com.example.Weather.WeatherApplication.domain.WeatherRequestDetails;
import com.example.Weather.WeatherApplication.entity.*;
import com.example.Weather.WeatherApplication.provider.GeocodingProvider;
import com.example.Weather.WeatherApplication.provider.Res10Provider;
import com.example.Weather.WeatherApplication.provider.WeatherProvider;
import com.example.Weather.WeatherApplication.transformer.GeocodingCoordinatedTranformer;
import com.example.Weather.WeatherApplication.transformer.OpenWeatherTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class WeatherService {
    @Autowired
    private GeoJPAEntity geoJPAEntity;
    @Autowired
    private Response10Repository response10Repository;
    private GeocodingProvider geocodingProvider;
    private GeocodingCoordinatedTranformer geocodingCoordinatedTranformer;
    private WeatherProvider weatherProvider;
    private Res10Provider res10Provider;
    private OpenWeatherTransformer openWeatherTransformer;


    @Autowired
    public WeatherService(final GeocodingProvider geocodingProvider,
                          final GeocodingCoordinatedTranformer geocodingCoordinatedTranformer,
                          final WeatherProvider weatherProvider,
                          final OpenWeatherTransformer openWeatherTransformer,
                          final Res10Provider res10Provider) {
        this.geocodingProvider = geocodingProvider;
        this.geocodingCoordinatedTranformer = geocodingCoordinatedTranformer;
        this.weatherProvider = weatherProvider;
        this.openWeatherTransformer = openWeatherTransformer;
        this.res10Provider = res10Provider;
    }
    public GeocodingCoordinatesEntity getPinDetails(WeatherRequestDetails weatherRequestDetails) throws Exception {
        GeocodingCoordinatesEntity geocodingCoordinatesEntity = geoJPAEntity.findByPincode(weatherRequestDetails.getZipcode());
        if (geocodingCoordinatesEntity != null) {
            log.info("Fetched from DB");
            return geocodingCoordinatesEntity;
        }
        geocodingCoordinatesEntity = geocodingProvider.
                getCoordinates(weatherRequestDetails);
        geoJPAEntity.save(geocodingCoordinatesEntity);
        return geocodingCoordinatesEntity;
    }

    public WeatherResponse getWeather(WeatherRequestDetails weatherRequestDetails) throws Exception {
        //    Get Latitude and Longitude
        // if exists dont call api
        final CityCoordinates cityCoordinates = geocodingCoordinatedTranformer
                .transformToDomain
                        (geocodingProvider.
                                getCoordinates(weatherRequestDetails));

        //    Get weather for geo coordinates
        final CityWeather cityWeather = openWeatherTransformer.transformToDomain(weatherProvider.getWeather(cityCoordinates));
        return openWeatherTransformer.transformToEntity(cityWeather);
    }

    public Response10 get10(WeatherRequestDetails weatherRequestDetails) throws Exception {

        final CityCoordinates cityCoordinates = geocodingCoordinatedTranformer
                .transformToDomain
                        (geocodingProvider.
                                getCoordinates(weatherRequestDetails));
        //    Get weather for geo coordinates
        Optional<Response10> optionalResponse10 = response10Repository.findByLatitudeAndLongitude(
                cityCoordinates.getLatitude().toString(),
                cityCoordinates.getLongitude().toString());

        if (optionalResponse10.isPresent()) {
            log.info("Fetched from DB");
            return optionalResponse10.get();
        } else {
            Response10 response10 = res10Provider.getWeatherNext10(cityCoordinates);
            log.info(response10.toString());
            //response10Repository.save(response10);
            return response10;
        }
    }

    public Response10 get10BasedonDate(WeatherRequestDetails weatherRequestDetails, String date) throws Exception {

        final CityCoordinates cityCoordinates = geocodingCoordinatedTranformer
                .transformToDomain
                        (geocodingProvider.
                                getCoordinates(weatherRequestDetails));

        //    Get weather for geo coordinates

        Optional<Response10> optionalResponse10 = response10Repository.findByLatitudeLongitudeAndDate(
                cityCoordinates.getLatitude().toString(),
                cityCoordinates.getLongitude().toString(), date);

        if (optionalResponse10.isPresent()) {
            log.info("Fetched from DB");
            return optionalResponse10.get();
        } else {
            Response10 response10 = res10Provider.getWeatherNext10(cityCoordinates);
            log.info(response10.toString());
            //response10Repository.save(response10);
            return response10;
        }
    }
}