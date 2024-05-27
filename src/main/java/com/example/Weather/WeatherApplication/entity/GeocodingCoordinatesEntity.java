package com.example.Weather.WeatherApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class GeocodingCoordinatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;
    @JsonProperty("lat")
    @Column(name = "latitude")
    private String latitude;
    @JsonProperty("lon")
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "pincode")
    private String pincode;

}
