package com.example.clieeeent.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class flightsEntity{
    private Long id_flights;

    private String path;


    private String price;

    private aircraftEntity air;

    @Override
    public String toString(){
        return path;
    }
}
