package com.example.clieeeent.controller;

import com.example.clieeeent.entity.UsersEntity;
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

import static com.example.clieeeent.controller.appController.usersData;

public class addUserController {

    public static String api = "http://localhost:2825/api/";
    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();
    @FXML
    private TextField f_nameField;
    @FXML
    private TextField l_nameField;
    @FXML
    private TextField s_nameField;
    @FXML
    private TextField code_passportField;
    @FXML
    private ComboBox<String> roleField;



    private Stage dialogStage;
    private UsersEntity users;
  //  private Stage editStudentStage;
    private boolean okClicked = false;
    public void setDialogStage (Stage dialogStage) {this.dialogStage = dialogStage;}
    public boolean isOkClicked(){return okClicked;}

    public void setRole(UsersEntity user) throws IOException {
        List<roleEntity> roles = getRolesFromDatabase();
        List<String> roleIds = roles.stream()
                .map(role -> String.valueOf(role.getId_role()))
                .collect(Collectors.toList());
        roleField.setItems(FXCollections.observableList(roleIds));

    }


    public List<roleEntity> getRolesFromDatabase() throws IOException {

        String res = http.get(api, "role/all");
        System.out.println(res);
        List<roleEntity> Roles = new ArrayList<>();

        JsonObject responseObj = gson.fromJson(res, JsonObject.class);
        JsonArray dataArray = responseObj.getAsJsonArray("data");

        for (JsonElement element : dataArray) {
            JsonObject dataObj = element.getAsJsonObject();

            long id_role = dataObj.get("id_role").getAsLong();
            String name = dataObj.get("name").getAsString();

            roleEntity role = new roleEntity();
            role.setId_role(id_role);
            role.setName(name);

            Roles.add(role);


        }
        return Roles;
    }

    @FXML
    private void handleOk() throws Exception {
        UsersEntity newUser = new UsersEntity();
        newUser.setF_name(f_nameField.getText());
        newUser.setL_name(l_nameField.getText());
        newUser.setS_name(s_nameField.getText());
        newUser.setCode_passport(Integer.parseInt(code_passportField.getText()));
        String selectedRole = roleField.getValue();
        roleEntity role = new roleEntity();
        role.setId_role(Long.valueOf(selectedRole));
        newUser.setRole(role);
        String userJson = gson.toJson(newUser);

        // Отправьте запрос на сервер для сохранения данных пользователя
        String response = http.post(api + "users/add", userJson);

        // Обработайте ответ сервера и выполните необходимые действия

        okClicked = true;
        dialogStage.close();
       appController.getDatarab();
       appController.getDataUsers();


    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public static void addUser(UsersEntity group) throws IOException {
        System.out.println(group.toString());

        System.out.println(http.post(api+"users/add", gson.toJson(group).toString()));
    }
}
