package com.example.clieeeent.controller;



import com.example.clieeeent.entity.departuresEntity;
import com.example.clieeeent.entity.flightsEntity;
import com.example.clieeeent.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class appController {

    public static String api = "http://localhost:2825/api/";
    public static ObservableList<departuresEntity> flightsData = FXCollections.observableArrayList();

    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    @FXML
    public TableView<departuresEntity> tableFlights;


    @FXML
    private TableColumn<departuresEntity, String> flightsPath;
    @FXML
    private TableColumn<departuresEntity, String> Seats;

    @FXML
    private TableColumn<departuresEntity, String> flightsPrice;




    @FXML
    private void initialize() throws Exception {
        getDataFlights();
        updateTableFlights();
    }
    public static void getDataFlights() throws Exception {
        String res = http.get(api,"departures/all");
        System.out.println(res);
        List<String> seatsList = new ArrayList<>();
        List<String> pathList = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject dataObj = element.getAsJsonObject();
            JsonObject flightsObj = dataObj.getAsJsonObject("id_flights");
            String seats = dataObj.getAsJsonObject("id_aricraft").get("seats").getAsString();
            String path = flightsObj.get("path").getAsString();

            seatsList.add(seats);
            pathList.add(path);
        }

        for (int i = 0; i < seatsList.size(); i++) {
            departuresEntity departure = new departuresEntity();
            departure.setSeats(Integer.parseInt(seatsList.get(i)));
            departure.setPath(pathList.get(i));
            flightsData.add(departure);
        }

        System.out.println(seatsList);
    }
    private void updateTableFlights() throws Exception {

        flightsPath.setCellValueFactory(new PropertyValueFactory<>("path"));
        Seats.setCellValueFactory(new PropertyValueFactory<>("seats"));

        tableFlights.setItems(flightsData);
    }



}
