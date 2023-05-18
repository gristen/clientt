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

//public class editUserController {
//    private Stage editAuthorStage;
//    private boolean okClicked = false;
//    public void setDialogStage (Stage dialogStage) {this.editAuthorStage = dialogStage;}
//    public boolean isOkClicked(){return okClicked;}
public class editUserController {
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
    private UsersEntity user;
    private int userID;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(UsersEntity user) throws IOException {
        this.user = user;
        List<roleEntity> roles = getRolesFromDatabase();

        f_nameField.setText(user.getF_name());
        l_nameField.setText(user.getL_name());
        s_nameField.setText(user.getS_name());
        code_passportField.setText(String.valueOf(user.getCode_passport()));

        List<String> roleIds = roles.stream()
                .map(role -> String.valueOf(role.getId_role()))
                .collect(Collectors.toList());
        roleField.setItems(FXCollections.observableList(roleIds));

        // Установите значение id в поле user
        user.setId(user.getId());
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
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws Exception {
        String firstName = f_nameField.getText();
        String lastName = l_nameField.getText();
        String surname = s_nameField.getText();
        String codePassport = code_passportField.getText();
        String selectedRole = roleField.getValue();

        // Проверка наличия данных в обязательных полях
        if (firstName.isEmpty() || lastName.isEmpty() || surname.isEmpty() || codePassport.isEmpty() || selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Пустые поля");
            alert.setHeaderText("Пожалуйста, заполните все обязательные поля");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если есть пустые поля
        }

        // Проверка, что поле code_passport является числом
        int codePassportNumeric;
        try {
            codePassportNumeric = Integer.parseInt(codePassport);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Некорректное значение");
            alert.setHeaderText("Поле 'Код паспорта' должно быть числом");
            alert.showAndWait();
            return; // Останавливаем выполнение метода, если некорректное значение поля code_passport
        }

        // Дополнительные проверки, если необходимо
        // ...

        // Установка значений полей пользователя
        user.setF_name(firstName);
        user.setL_name(lastName);
        user.setS_name(surname);
        user.setCode_passport(codePassportNumeric);
        user.getRole().setId_role(Long.valueOf(selectedRole));

        // Преобразование объекта пользователя в JSON
        String userJson = gson.toJson(user);

        // Отправка запроса на сервер для обновления данных пользователя
        String response = http.post(api + "users/update", userJson);

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


}




