package com.example.Weather.WeatherApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class TempEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String date;
    private String weatherDescription;
    @JsonProperty("temp")
    private String temp;
    @JsonProperty("feels_like")
    private String feels_like;
    @JsonProperty("temp_min")
    private String temp_min;
    @JsonProperty("temp_max")
    private String temp_max;
    @JsonProperty("humidity")
    private String humidity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response10_id")
    @JsonIgnore
    private Response10 response10;

    @Override
    public String toString() {
        return "TempEntity{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", temp='" + temp + '\'' +
                ", feels_like='" + feels_like + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp_max='" + temp_max + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
