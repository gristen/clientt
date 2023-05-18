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
import javafx.scene.control.Alert;
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
        String firstName = f_nameField.getText();
        String lastName = l_nameField.getText();
        String secondName = s_nameField.getText();
        String codePassportText = code_passportField.getText();
        String selectedRole = roleField.getValue();

        // Проверка наличия данных в обязательных полях
        if (firstName.isEmpty() || lastName.isEmpty() || codePassportText.isEmpty()|| selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Пустые поля");
            alert.setHeaderText("Пожалуйста, заполните обязательные поля");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если есть пустые поля
        }

        // Проверка, является ли код паспорта числом
        int codePassport;
        try {
            codePassport = Integer.parseInt(codePassportText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Некорректный код паспорта");
            alert.setHeaderText("Пожалуйста, введите корректный код паспорта (число)");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если код паспорта некорректный
        }
        if (codePassport <=0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Некорректный код паспорта");
            alert.setHeaderText("код паспорта не может быть меньше нуля");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если код паспорта некорректный
        }

        // Создание объекта пользователя и установка значений полей
        UsersEntity newUser = new UsersEntity();
        newUser.setF_name(firstName);
        newUser.setL_name(lastName);
        newUser.setS_name(secondName);
        newUser.setCode_passport(codePassport);


        roleEntity role = new roleEntity();
        role.setId_role(Long.valueOf(selectedRole));
        newUser.setRole(role);
        String userJson = gson.toJson(newUser);

        // Отправка запроса на сервер для сохранения данных пользователя
        String response = http.post(api + "users/add", userJson);

        // Обработка ответа сервера и выполнение необходимых действий

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
