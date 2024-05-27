package com.example.Weather.WeatherApplication.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempEntityRepository extends JpaRepository<TempEntity,Long> {
    //Optional<TempEntity> findByLatitudeAndLongitude(String latitude, String longitude);
}
