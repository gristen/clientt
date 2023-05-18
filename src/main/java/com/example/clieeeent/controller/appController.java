package com.example.clieeeent.controller;

import com.example.clieeeent.HelloApplication;
import com.example.clieeeent.entity.UsersEntity;
import com.example.clieeeent.entity.departuresEntity;

import com.example.clieeeent.entity.flightsEntity;
import com.example.clieeeent.entity.roleEntity;
import com.example.clieeeent.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.clieeeent.HelloApplication.showPersonEditDialog;

public class appController {

    public static String api = "http://localhost:2825/api/";
    public static ObservableList<departuresEntity> flightsData = FXCollections.observableArrayList();
    public static ObservableList<UsersEntity> usersData = FXCollections.observableArrayList();
    public static ObservableList<UsersEntity> rabData = FXCollections.observableArrayList();
    public static ObservableList<flightsEntity> fliData = FXCollections.observableArrayList();

    public static ObservableList<flightsEntity> seatchDataFlights = FXCollections.observableArrayList();

    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    @FXML
    public TableView<departuresEntity> tableFlights;

    @FXML
    private TableColumn<departuresEntity, String> idDepart;
    @FXML
    private TableColumn<departuresEntity, String> flightsPath;
    @FXML
    private TableColumn<departuresEntity, String> Seats;


    @FXML
    private TableColumn<departuresEntity, String> flightsPrice;

    @FXML
    private TextField SearchFieldProducts;
    @FXML
    public TableView<flightsEntity> tableFli;

    @FXML
    private TableColumn<flightsEntity, String> fliPath;
    @FXML
    private TableColumn<flightsEntity, String> fliPrice;
    @FXML
    private TableColumn<flightsEntity, String> idFlights;

    @FXML

    public TableView<UsersEntity> tableUsers;

    @FXML
    private TableColumn<UsersEntity, String> numberUser;
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
    private TableColumn<UsersEntity, String> numberRab;


    @FXML
    private void initialize() throws Exception {
        getDataDepart();
        updateTableFlights();
        getDatarab();
        getDataUsers();
        getDataFlights();

        updateTableUsers();
        updateTableRab();
        updateTableFli();

    }
    public static void getDataDepart() throws Exception {
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


            long idDepart = dataObj.get("id_depart").getAsLong();
            JsonObject flightsObj = dataObj.getAsJsonObject("id_flights");
            String seats = dataObj.getAsJsonObject("id_aricraft").get("seats").getAsString();

            String path = flightsObj.get("path").getAsString();
            String price = flightsObj.get("price").getAsString();


            seatsList.add(seats);
            pathList.add(path);
            priceList.add(price);
            idList.add(String.valueOf(idDepart));
        }

        for (int i = 0; i < seatsList.size(); i++) {
            departuresEntity departure = new departuresEntity();
            departure.setSeats(Integer.parseInt(seatsList.get(i)));
            departure.setPath(pathList.get(i));
            departure.setPrice(priceList.get(i));
            departure.setId_depart((long) i+1);

            flightsData.add(departure);
        }

       // System.out.println(seatsList);
    }

    public static   void getDataFlights() throws Exception {
        String res = http.get(api, "flights/all");
        System.out.println(res);
        List<flightsEntity> flights = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject userObj = element.getAsJsonObject();


                String path = userObj.get("path").getAsString();
                String price = userObj.get("price").getAsString();

                int id = Integer.parseInt(userObj.get("id_flights").getAsString());

                flightsEntity user = new flightsEntity();
                user.setId_flights((long) id);
                user.setPath(path);
                user.setPrice(price);

            flights.add(user);
        }
        fliData.clear();
        fliData.addAll(flights);
    }

    @FXML
    private void click_removeUser() throws Exception {
        UsersEntity selectedPerson = tableUsers.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            selectedPerson.getId();
            http.delete(api+"users/delete/?id=", (long) selectedPerson.getId());
            usersData.remove(selectedPerson);
            getDataUsers();
            getDatarab();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Отсутсвует выбраный пользователь!");
            alert.setContentText("Пожалуйста, выберите пользователя из таблицы");
            alert.showAndWait();
        }
    }

    @FXML
    private void click_removeFlights() throws Exception {
        flightsEntity selectedFlight = tableFli.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            long flightId = selectedFlight.getId_flights();
            http.delete(api+"flights/delete/?id=", flightId);
            tableFli.getItems().remove(selectedFlight);
            getDataFlights();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Отсутствует выбранный рейс!");
            alert.setContentText("Пожалуйста, выберите рейс из таблицы");
            alert.showAndWait();
        }
    }

    private void updateTableFlights() throws Exception {
        idDepart.setCellValueFactory(new PropertyValueFactory<>("id_depart"));
        flightsPath.setCellValueFactory(new PropertyValueFactory<>("path"));
        flightsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Seats.setCellValueFactory(new PropertyValueFactory<>("seats"));

        tableFli.setItems(fliData);
    }

    public static   void getDatarab() throws Exception {
        String res = http.get(api, "users/all");
        System.out.println(res);
        List<UsersEntity> users = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("users");

        for (JsonElement element : dataArray) {
            JsonObject userObj = element.getAsJsonObject();
            JsonObject roleObj = userObj.getAsJsonObject("role");

            int id_role = roleObj.get("id_role").getAsInt();

            if (id_role > 1) {
                System.out.println(userObj);
                String f_name = userObj.get("f_name").getAsString();
                String l_name = userObj.get("l_name").getAsString();
                String s_name = userObj.get("s_name").getAsString();
                int code_passport = userObj.get("code_passport").getAsInt();
                int id = Integer.parseInt(userObj.get("id").getAsString());

                UsersEntity user = new UsersEntity();
                user.setId(id);
                user.setF_name(f_name);
                user.setL_name(l_name);
                user.setS_name(s_name);
                user.setCode_passport(code_passport);

                // Создайте объект roleEntity и установите его идентификатор
                roleEntity role = new roleEntity();
                role.setId_role((long) id_role);

                // Установите role в поле пользователя
                user.setRole(role);

                users.add(user);
            }
        }

        // Обновите таблицу с данными пользователей
        rabData.clear();
        rabData.addAll(users);
    }
    public static   void getDataUsers() throws Exception {
        String res = http.get(api, "users/all");
        System.out.println(res);
        List<UsersEntity> users = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("users");

        for (JsonElement element : dataArray) {
            JsonObject userObj = element.getAsJsonObject();
            JsonObject roleObj = userObj.getAsJsonObject("role");

            int id_role = roleObj.get("id_role").getAsInt();

                String f_name = userObj.get("f_name").getAsString();
                String l_name = userObj.get("l_name").getAsString();
                String s_name = userObj.get("s_name").getAsString();
                int code_passport = userObj.get("code_passport").getAsInt();
                int id = Integer.parseInt(userObj.get("id").getAsString());

                UsersEntity user = new UsersEntity();
                user.setId(id);
                user.setF_name(f_name);
                user.setL_name(l_name);
                user.setS_name(s_name);
                user.setCode_passport(code_passport);

                // Создайте объект roleEntity и установите его идентификатор
                roleEntity role = new roleEntity();
                role.setId_role((long) id_role);

                // Установите role в поле пользователя
                user.setRole(role);

                users.add(user);

        }

        // Обновите таблицу с данными пользователей
        usersData.clear();
        usersData.addAll(users);
    }
    @FXML
    private void  SearchDataFlights() throws IOException{
        String tov = http.get("http://localhost:2825/api/flights/name?name=", SearchFieldProducts.getText());
        System.out.println(tov);
        JsonObject base = gson.fromJson(tov, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for(int i = 0; i < dataArr.size();i++){
            flightsEntity newpro = gson.fromJson(dataArr.get(i).toString(),flightsEntity.class);
            seatchDataFlights.add(newpro);
        }
        fliPath.setCellValueFactory(new PropertyValueFactory<flightsEntity, String>("path"));
        fliPath.setCellValueFactory(new PropertyValueFactory<flightsEntity, String>("price"));
        idFlights.setCellValueFactory(new PropertyValueFactory<flightsEntity, String>("id_flights"));

        tableFli.setItems(seatchDataFlights);
    }

    private void updateTableUsers() {
        f_name.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        l_name.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        s_name.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        pass.setCellValueFactory(new PropertyValueFactory<>("code_passport"));
        numberUser.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableUsers.setItems(usersData);
    }

    private void updateTableFli() {
        idFlights.setCellValueFactory(new PropertyValueFactory<>("id_flights"));
        fliPath.setCellValueFactory(new PropertyValueFactory<>("path"));
        fliPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        tableFli.setItems(fliData);
    }
    @FXML
    void editUser(ActionEvent event) {
        UsersEntity selectedUser = tableRab.getSelectionModel().getSelectedItem();

        HelloApplication.showPersonEditDialog(selectedUser);
    }

    @FXML
    void addUser(ActionEvent event) {
        UsersEntity user = new UsersEntity();
        HelloApplication.showUserAddDialog(user);
    }

    @FXML
    void addFlights(ActionEvent event) {
        flightsEntity fli = new flightsEntity();
        HelloApplication.showFlightsAddDialog(fli);
    }

    @FXML
    void editFlights(ActionEvent event) {
        flightsEntity sele = tableFli.getSelectionModel().getSelectedItem();
        HelloApplication.showFlightsEditDialog(sele);
    }





    private void updateTableRab() {
        nameRab.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        l_rab.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        s_nameRab.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        roleRab.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRole().getId_role())));
        passRab.setCellValueFactory(new PropertyValueFactory<>("code_passport"));
        numberRab.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableRab.setItems(rabData);
    }





















}



