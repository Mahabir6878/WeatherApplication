package com.example.Weather.WeatherApplication.entity;

import com.example.Weather.WeatherApplication.entity.Response10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Response10Repository extends JpaRepository<Response10, Long> {
    Optional<Response10> findByLatitudeAndLongitude(String latitude, String longitude);
    @Query("SELECT r FROM Response10 r JOIN FETCH r.tempEntities t WHERE r.latitude = :latitude AND r.longitude = :longitude AND t.date LIKE :date%")
    Optional<Response10> findByLatitudeLongitudeAndDate(String latitude,String longitude,String date);

}
