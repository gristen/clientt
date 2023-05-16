package com.example.clieeeent.response;

import com.example.clieeeent.entity.flightsEntity;

import lombok.Data;

@Data
public class FlightsResponse extends BaseResponse {
    public FlightsResponse(boolean success, String message, flightsEntity book){
        super(success,message);
        this.book=book;
    }
    public FlightsResponse(flightsEntity publisher){
        super(true,"Book Data");
    }
    private flightsEntity book;
}
