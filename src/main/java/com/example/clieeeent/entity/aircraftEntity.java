package com.example.clieeeent.entity;

import lombok.Data;

@Data
public class aircraftEntity {

    private Long Id_aricraft;

    private String Aircraft_type;

    private int Seats;

    @Override
    public String toString(){
        return Seats + ' ' + Aircraft_type;
    }
}
