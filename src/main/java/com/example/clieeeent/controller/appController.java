package com.example.clieeeent.controller;



import com.example.clieeeent.entity.UsersEntity;
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
    public static ObservableList<UsersEntity> usersData = FXCollections.observableArrayList();
    public static ObservableList<UsersEntity> rabData = FXCollections.observableArrayList();

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
    public TableView<UsersEntity> tableUsers;
    @FXML
    private TableColumn<UsersEntity, String> f_name;
    @FXML
    private TableColumn<UsersEntity, String> l_name;
    @FXML
    private TableColumn<UsersEntity, String> s_name;
    @FXML
    private TableColumn<UsersEntity, String> role;
    @FXML
    private TableColumn<UsersEntity, String> pass;
    @FXML
    public TableView<UsersEntity> tableRab;
    @FXML
    private TableColumn<UsersEntity, String> nameRab;
    @FXML
    private TableColumn<UsersEntity, String> l_rab;
    @FXML
    private TableColumn<UsersEntity, String> s_nameRab;
    @FXML
    private TableColumn<UsersEntity, String> roleRab;
    @FXML
    private TableColumn<UsersEntity, String> passRab;


    @FXML
    private void initialize() throws Exception {
        getDataFlights();
        updateTableFlights();
        getDataUsers();
        updateTableUsers();
        updateTableRab();
        getDatarab();
    }
    public static void getDataFlights() throws Exception {
        String res = http.get(api,"departures/all");
        System.out.println(res);
        List<String> seatsList = new ArrayList<>();
        List<String> pathList = new ArrayList<>();
        List<String> priceList = new ArrayList<>();
        List<String> idList = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject dataObj = element.getAsJsonObject();
            JsonObject flightsObj = dataObj.getAsJsonObject("id_flights");
            String seats = dataObj.getAsJsonObject("id_aricraft").get("seats").getAsString();

            String path = flightsObj.get("path").getAsString();
            String price = flightsObj.get("price").getAsString();

            seatsList.add(seats);
            pathList.add(path);
            priceList.add(price);
        }

        for (int i = 0; i < seatsList.size(); i++) {
            departuresEntity departure = new departuresEntity();
            departure.setSeats(Integer.parseInt(seatsList.get(i)));
            departure.setPath(pathList.get(i));
            departure.setPrice(priceList.get(i));
            flightsData.add(departure);
        }

        System.out.println(seatsList);
    }
    private void updateTableFlights() throws Exception {

        flightsPath.setCellValueFactory(new PropertyValueFactory<>("path"));
        flightsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Seats.setCellValueFactory(new PropertyValueFactory<>("seats"));

        tableFlights.setItems(flightsData);
    }

    public void getDataUsers() throws Exception {
        String res = http.get(api, "users/all");
        System.out.println(res);
        List<String> f_nameList = new ArrayList<>();
        List<String> l_nameList = new ArrayList<>();
        List<String> s_nameList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<Integer> code_passportList = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("users");

        for (JsonElement element : dataArray) {
            JsonObject userObj = element.getAsJsonObject();

            String f_name = userObj.get("f_name").getAsString();
            String l_name = userObj.get("l_name").getAsString();
            String s_name = userObj.get("s_name").getAsString();
            String role = userObj.get("role").getAsJsonObject().get("name").getAsString();
            int code_passport = userObj.get("code_passport").getAsInt();

            f_nameList.add(f_name);
            l_nameList.add(l_name);
            s_nameList.add(s_name);
            roleList.add(role);
            code_passportList.add(code_passport);
        }

        for (int i = 0; i < f_nameList.size(); i++) {
            UsersEntity user = new UsersEntity();
            user.setF_name(f_nameList.get(i));
            user.setL_name(l_nameList.get(i));
            user.setS_name(s_nameList.get(i));
            user.setRole(roleList.get(i));
            user.setCode_passport(code_passportList.get(i));
            usersData.add(user);
        }
    }

    private void updateTableUsers() {
        f_name.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        l_name.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        s_name.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
       pass.setCellValueFactory(new PropertyValueFactory<>("code_passport"));

        tableUsers.setItems(usersData);
    }



    public void getDatarab() throws Exception {
        String res = http.get(api, "users/all");
        System.out.println(res);
        List<String> f_nameList = new ArrayList<>();
        List<String> l_nameList = new ArrayList<>();
        List<String> s_nameList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<Integer> code_passportList = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("users");

        for (JsonElement element : dataArray) {
            JsonObject userObj = element.getAsJsonObject();
            JsonObject roleObj = userObj.getAsJsonObject("role");
            int id_role = roleObj.get("id_role").getAsInt();

            // Фильтрация пользователей по id_role
            if (id_role > 1) {
                String f_name = userObj.get("f_name").getAsString();
                String l_name = userObj.get("l_name").getAsString();
                String s_name = userObj.get("s_name").getAsString();
                String role = roleObj.get("name").getAsString();
                int code_passport = userObj.get("code_passport").getAsInt();

                f_nameList.add(f_name);
                l_nameList.add(l_name);
                s_nameList.add(s_name);
                roleList.add(role);
                code_passportList.add(code_passport);
            }
        }

        for (int i = 0; i < f_nameList.size(); i++) {
            UsersEntity user = new UsersEntity();
            user.setF_name(f_nameList.get(i));
            user.setL_name(l_nameList.get(i));
            user.setS_name(s_nameList.get(i));
            user.setRole(roleList.get(i));
            user.setCode_passport(code_passportList.get(i));
            rabData.add(user);
        }

    }

    private void updateTableRab() {
        nameRab.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        l_rab.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        s_nameRab.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        roleRab.setCellValueFactory(new PropertyValueFactory<>("role"));
        passRab.setCellValueFactory(new PropertyValueFactory<>("code_passport"));

        tableRab.setItems(rabData);
    }





















}



