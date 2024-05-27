package com.example.Weather.WeatherApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Response10 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "response10", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TempEntity> tempEntities;
    private String latitude;
    private String longitude;

    public Response10(List<TempEntity> list, String latitude, String longitude) {
        this.tempEntities = list;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Response10{" +
                "id=" + id +
                ", tempEntities=" + tempEntities +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
