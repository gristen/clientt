package com.example.clieeeent.response;


import com.example.clieeeent.entity.flightsEntity;
import lombok.Data;

import java.util.List;

@Data
public class FlightsListResponse extends BaseResponse {
    public FlightsListResponse(List<flightsEntity> data) {
        super(true, "Books");
        this.data=data;
    }
    private List<flightsEntity> data;
}
