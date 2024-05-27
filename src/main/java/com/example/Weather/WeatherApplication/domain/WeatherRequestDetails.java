package com.example.Weather.WeatherApplication.domain;
import jakarta.persistence.Entity;
import lombok.*;

@Builder
@Getter
@Setter
public class WeatherRequestDetails {
    private String zipcode;
}
