package com.example.Weather.WeatherApplication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Res10Coordinates {
    private String dt;
    private String sunrise;
    private String sunset;
    private String temp;
}
