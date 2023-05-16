package com.example.clieeeent.entity;

import lombok.Data;

@Data
public class departuresEntity {
   private Long id_depart;
   private flightsEntity id_flights;
   private aircraftEntity id_aricraft;
  // private usersEntity id_user;
  private int seats;
   private String path;
   private String price;

   public int getSeats() {
      return seats;
   }

   public void setSeats(int seats) {
      this.seats = seats;
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
   }
    public void setPrice(String price) {
        this.price = price;
    }
}