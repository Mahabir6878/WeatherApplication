package com.example.Weather.WeatherApplication.resource;

import com.example.Weather.WeatherApplication.domain.WeatherRequestDetails;
import com.example.Weather.WeatherApplication.entity.GeocodingCoordinatesEntity;
import com.example.Weather.WeatherApplication.entity.Response10;
import com.example.Weather.WeatherApplication.entity.WeatherResponse;
import com.example.Weather.WeatherApplication.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weatherapp")
public class WeatherResource {
    private WeatherService weatherService;

    @Autowired
   public WeatherResource(final WeatherService weatherService){
       this.weatherService=weatherService;
   }


    @GetMapping("/pincode/{pin}")
    public GeocodingCoordinatesEntity test(@PathVariable("pin") String pin) throws Exception {

        final WeatherRequestDetails weatherRequestDetails= WeatherRequestDetails.builder()
                .zipcode(pin)
                .build();
        return  weatherService.getPinDetails(weatherRequestDetails);
    }

    @GetMapping("/zipcode/{zip}")
    public @ResponseBody WeatherResponse weatherResponse(@PathVariable("zip") String zipcode) throws Exception {
        final WeatherRequestDetails weatherRequestDetails= WeatherRequestDetails.builder()
                .zipcode(zipcode)
                .build();

        return weatherService.getWeather(weatherRequestDetails);
    }

    @GetMapping("/zip/{zip}")
    public @ResponseBody Response10 response10(@PathVariable("zip")String zipcode) throws Exception {
        final WeatherRequestDetails weatherRequestDetails=WeatherRequestDetails.builder()
                .zipcode(zipcode)
                .build();

        return weatherService.get10(weatherRequestDetails);


    }

    @GetMapping("/zipD/{zip}/{date}")
    public @ResponseBody Response10 response10(@PathVariable("zip")String zipcode,@PathVariable("date")String date) throws Exception {
        final WeatherRequestDetails weatherRequestDetails=WeatherRequestDetails.builder()
                .zipcode(zipcode)
                .build();

        return weatherService.get10BasedonDate(weatherRequestDetails,date);


    }


}
