package com.example.clieeeent.controller;

import com.example.clieeeent.entity.UsersEntity;
import com.example.clieeeent.entity.aircraftEntity;
import com.example.clieeeent.entity.flightsEntity;
import com.example.clieeeent.entity.roleEntity;
import com.example.clieeeent.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class addFlightsController {
    public static String api = "http://localhost:2825/api/";
    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();
    @FXML
    private TextField path;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<String> seats;

    private Stage dialogStage;
    private boolean okClicked = false;
    public void setDialogStage (Stage dialogStage) {this.dialogStage = dialogStage;}
    public boolean isOkClicked(){return okClicked;}

//    public void setSeats(flightsEntity user) throws IOException {
//        List<aircraftEntity> seatsList = getSeatsFromDatabase();
//        List<String> seatNames = seatsList.stream()
//                .map(aircraft -> aircraft.getSeats() + " " + aircraft.getAircraft_type())
//                .collect(Collectors.toList());
//        seats.setItems(FXCollections.observableList(seatNames));
//    }

    public List<aircraftEntity> getSeatsFromDatabase() throws IOException {

        String res = http.get(api, "aircraft/all");
        System.out.println(res);
        List<aircraftEntity> Roles = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject dataObj = element.getAsJsonObject();

            long seats = dataObj.get("seats").getAsLong();
            String aircraft_type = dataObj.get("aircraft_type").getAsString();

            aircraftEntity role = new aircraftEntity();
            role.setSeats((int) seats);
            role.setAircraft_type(aircraft_type);

            Roles.add(role);


        }
        return Roles;
    }
    @FXML
    private void addFlight() throws Exception {
        flightsEntity newFlight = new flightsEntity();
        newFlight.setPath(path.getText());
        newFlight.setPrice(price.getText());
        String flightJson = gson.toJson(newFlight);

        String response = http.post(api + "flights/add", flightJson);

        okClicked = true;
        dialogStage.close();

        appController.getDataFlights();

    }


}
