package com.example.Weather.WeatherApplication.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoJPAEntity extends JpaRepository<GeocodingCoordinatesEntity,Long> {

    public GeocodingCoordinatesEntity findByPincode(String pin);
}
